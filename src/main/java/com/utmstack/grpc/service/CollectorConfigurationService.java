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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CollectorConfigurationService {
    private static final String CLASSNAME = "CollectorConfigurationService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);
    private CollectorConfigurationServiceGrpc.CollectorConfigurationServiceStub nonBlockingStub;
    private ManagedChannel grpcManagedChannel;

    private StreamObserver<CollectorConfig> configStreamObserver;

    public CollectorConfigurationService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorConfigurationServiceGrpc.newStub(grpcManagedChannel);
    }

    private ConfigKnowledge callForCollectorConfig(CollectorConfig request) throws CollectorConfigurationGrpcException {
        final String ctx = CLASSNAME + ".callForCollectorConfig";
        final CountDownLatch finishLatch = new CountDownLatch(1);
        ConfigKnowledge confirmationKnowledge = ConfigKnowledge.newBuilder().build();
        try {
            configStreamObserver = nonBlockingStub
                    .withInterceptors(new GrpcInternalKeyInterceptor()).collectorConfigStream(new StreamObserver<>() {

                @Override
                public void onNext(ConfigKnowledge configKnowledge) {
                    confirmationKnowledge.toBuilder().setAccepted(configKnowledge.getAccepted());
                    confirmationKnowledge.toBuilder().setRequestId(confirmationKnowledge.getRequestId());
                }

                @Override
                public void onError(Throwable cause) {
                    throw new RuntimeException("Executing call configuration, server responded with error: " + cause.getMessage());
                }

                @Override
                public void onCompleted() {
                     finishLatch.countDown();
                     logger.info(ctx + ": Executed successfully.");
                }
            });
            configStreamObserver.onNext(request);

            // Try to wait for the server response
            int counter = 5;
            while (counter > 0){
              finishLatch.await(5, TimeUnit.SECONDS);
              counter --;
            }
            if (StringUtil.isNullOrEmpty(confirmationKnowledge.getAccepted())) {
                throw new CollectorConfigurationGrpcException(ctx + ": Not responded since last 25 seconds");
            }
            return confirmationKnowledge;
        } catch (Exception e) {
            logger.error(ctx + ": " + e.getMessage());
            throw new CollectorConfigurationGrpcException(ctx + ": " + e.getMessage());
        }
    }

}
