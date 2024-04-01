package com.utmstack.grpc.service;

import agent.CollectorConfigurationServiceGrpc;
import agent.CollectorOuterClass.ConfigKnowledge;
import agent.CollectorOuterClass.CollectorConfig;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcInternalKeyInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the services related to collector configurations.
 */
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
     * Method to send a collector config to the server, this method wait 60 seconds for a server response.
     * This method uses the internal key interceptor, so, you need to set its value in the KeyStore.INTERNAL_KEY
     * This method is a "blocker" one, so call it in separated thread or will block your app till the server responds
     * or throws an error.
     *
     * @param request is the collector config to send
     * @return boolean with the server response
     * @throws CollectorConfigurationGrpcException if the server not answer with a valid ConfigKnowledge after 60 seconds
     */

    /** Variables used to work with the method getConfigStreamObserver
    * confirmationList holds all the server responses, the method checks if acknowledge of your request
    * is present
    */
    private final List<ConfigKnowledge> confirmationList = new ArrayList<>();

    public boolean insertCollectorConfig(CollectorConfig request) throws CollectorConfigurationGrpcException {
        final String ctx = CLASSNAME + ".insertCollectorConfig";
        final CountDownLatch finishLatch = new CountDownLatch(1);
        boolean result = false;
        try {
            // Getting the stream observer and sending the configuration
            StreamObserver<CollectorConfig> streamObserver = getConfigStreamObserver();
            streamObserver.onNext(request);

            // Try to wait for the server response
            int counter = 12;
            while (counter > 0) {
                Optional<ConfigKnowledge> acknowledge = confirmationList.stream().filter(c -> c.getRequestId().equals(request.getRequestId()) &&
                        (c.getAccepted().equals("true") || c.getAccepted().equals("yes"))).findFirst();
                if (acknowledge.isPresent()) {
                    confirmationList.remove(acknowledge.get());
                    result = true;
                    break;
                } else {
                    finishLatch.await(5, TimeUnit.SECONDS);
                    counter--;
                }
            }
            if (!result) {
                throw new CollectorConfigurationGrpcException("Server not responded or accepted the request in the last 60 seconds ...");
            }
        } catch (Exception e) {
            throw new CollectorConfigurationGrpcException(ctx + ": " + e.getMessage());
        }
        return result;
    }

    /**
     * Method to initialize the stream between client and the server
     */
    private StreamObserver<CollectorConfig> getConfigStreamObserver() throws CollectorConfigurationGrpcException {
        final String ctx = CLASSNAME + ".getConfigStreamObserver";

            try {
                configStreamObserver = nonBlockingStub
                        .withInterceptors(new GrpcInternalKeyInterceptor()).collectorConfigStream(new StreamObserver<>() {

                            @Override
                            public void onNext(ConfigKnowledge configKnowledge) {
                                confirmationList.add(configKnowledge);
                            }

                            @Override
                            public void onError(Throwable cause) {
                                logger.error(ctx + ": Initializing insertion channel, server responded with error: " + cause.getMessage());
                            }

                            @Override
                            public void onCompleted() {
                                logger.info(ctx + ": Executed successfully.");
                            }
                        });
            } catch (Exception e) {
                throw new CollectorConfigurationGrpcException(ctx + ": " + e.getMessage());
            }
        return configStreamObserver;
    }
    /**
     * Method to "on demand" clear the list of server results
     * */
    public void clearList() {
        confirmationList.clear();
    }

}
