package com.utmstack.grpc.service;

import agent.CollectorConfigurationServiceGrpc;
import agent.CollectorOuterClass.ConfigKnowledge;
import agent.CollectorOuterClass.CollectorConfig;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.GrpcInternalKeyInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the services related to collector configurations.
 * */
public class CollectorConfigurationService {
    private static final String CLASSNAME = "CollectorConfigurationService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);
    private final CollectorConfigurationServiceGrpc.CollectorConfigurationServiceStub nonBlockingStub;
    private final ManagedChannel grpcManagedChannel;

    private StreamObserver<CollectorConfig> configStreamObserver;

    public CollectorConfigurationService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorConfigurationServiceGrpc.newStub(grpcManagedChannel);
    }

    /**
     * Method to send a collector config to the server, this method wait 25 seconds for a server response.
     * This method uses the internal key interceptor, so, you need to set its value in the KeyStore.INTERNAL_KEY
     * @param request is the collector config to send
     * @return ConfigKnoledge with the server response
     * @throws CollectorConfigurationGrpcException if the server not answer with a valid ConfigKnoledge after 60 seconds
     * */
    private ConfigKnowledge callForCollectorConfig(CollectorConfig request) throws CollectorConfigurationGrpcException {
        final String ctx = CLASSNAME + ".callForCollectorConfig";
        final CountDownLatch finishLatch = new CountDownLatch(1);
        final List<ConfigKnowledge> confirmationList = new ArrayList<>();

        // Adding first element to ensure that always return a valid element
        confirmationList.add(ConfigKnowledge.newBuilder().build());

        try {
            configStreamObserver = nonBlockingStub
                    .withInterceptors(new GrpcInternalKeyInterceptor()).collectorConfigStream(new StreamObserver<>() {

                @Override
                public void onNext(ConfigKnowledge configKnowledge) {
                    confirmationList.set(0,configKnowledge);
                    finishLatch.countDown();
                }

                @Override
                public void onError(Throwable cause) {
                    finishLatch.countDown();
                    logger.error(ctx + ": Executing call configuration, server responded with error: " + cause.getMessage());
                }

                @Override
                public void onCompleted() {
                     finishLatch.countDown();
                     logger.info(ctx + ": Executed successfully.");
                }
            });
            configStreamObserver.onNext(request);
            // Complete the RPC call
            configStreamObserver.onCompleted();

            // Try to wait for the server response
            int counter = 12;
            while (counter > 0){
              finishLatch.await(5, TimeUnit.SECONDS);
              counter --;
            }

            // Check if the server responded
            ConfigKnowledge result = confirmationList.get(0);
            if (StringUtil.isNullOrEmpty(result.getAccepted())) {
                throw new CollectorConfigurationGrpcException("Server not responded since last 60 seconds");
            }
            return result;
        } catch (Exception e) {
            logger.error(ctx + ": " + e.getMessage());
            throw new CollectorConfigurationGrpcException(ctx + ": " + e.getMessage());
        }
    }

}
