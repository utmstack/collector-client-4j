package com.utmstack.grpc.service;

import agent.CollectorOuterClass.ConfigRequest;
import agent.CollectorOuterClass.RegisterRequest;
import agent.CollectorOuterClass.CollectorMessages;
import agent.CollectorOuterClass.CollectorConfig;
import agent.CollectorOuterClass.ListCollectorResponse;
import agent.Common.ListRequest;
import agent.CollectorServiceGrpc;
import agent.Common.AuthResponse;
import agent.Common.DeleteRequest;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.*;
import com.utmstack.grpc.service.iface.IExecuteActionOnError;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
import com.utmstack.grpc.util.StringUtil;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the crud operations of collectors
 */
public class CollectorService {
    private static final String CLASSNAME = "CollectorService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);

    private final CollectorServiceGrpc.CollectorServiceBlockingStub blockingStub;
    private final CollectorServiceGrpc.CollectorServiceStub nonBlockingStub;
    private final ManagedChannel grpcManagedChannel;
    private StreamObserver<CollectorMessages> collectorMessagesObserver;


    public CollectorService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorServiceGrpc.newStub(grpcManagedChannel);
        this.blockingStub = CollectorServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to register a collector.
     *
     * @param request       is the information of the collector to register.
     * @param connectionKey is the connection key to communicate internally.
     * @throws CollectorServiceGrpcException if the action can't be performed.
     */
    public AuthResponse registerCollector(RegisterRequest request, String connectionKey) throws CollectorServiceGrpcException {
        try {
            return blockingStub.withInterceptors(new GrpcConnectionKeyInterceptor().withConnectionKey(connectionKey),
                            new GrpcTypeInterceptor())
                    .registerCollector(request);
        } catch (Exception e) {
            String msg = "Error registering collector: " + e.getMessage();
            throw new CollectorServiceGrpcException(StringUtil.formatError(msg));
        }
    }

    /**
     * Method to get a collector configuration.
     *
     * @param request   is to let the server know what module is making a request.
     * @param collector is the collector which is requesting the configuration.
     * @throws CollectorServiceGrpcException if the action can't be performed.
     */
    public CollectorConfig requestCollectorConfig(ConfigRequest request, AuthResponse collector) throws CollectorServiceGrpcException {
        try {
            return blockingStub.withInterceptors(new GrpcKeyInterceptor()
                                    .withCollectorKey(collector.getKey()),
                            new GrpcIdInterceptor().withCollectorId(collector.getId()),
                            new GrpcTypeInterceptor())
                    .getCollectorConfig(request);
        } catch (Exception e) {
            String msg = "Error getting collector configuration: " + e.getMessage();
            throw new CollectorServiceGrpcException(StringUtil.formatError(msg));
        }
    }

    /**
     * Method to remove a collector.
     *
     * @param request   is th information of the collector to delete.
     * @param collector is the information of the collector to delete.
     * @throws CollectorServiceGrpcException if the action can't be performed.
     */
    public AuthResponse deleteCollector(DeleteRequest request, AuthResponse collector) throws CollectorServiceGrpcException {
        try {
            return blockingStub.withInterceptors(new GrpcKeyInterceptor()
                                    .withCollectorKey(collector.getKey()),
                            new GrpcIdInterceptor().withCollectorId(collector.getId()),
                            new GrpcTypeInterceptor())
                    .deleteCollector(request);
        } catch (Exception e) {
            String msg = "Error removing collector: " + e.getMessage();
            throw new CollectorServiceGrpcException(StringUtil.formatError(msg));
        }
    }


    /**
     * Method to initialize the collector messages stream between client and the server.
     *
     * @param toDoAction implementation of the action to execute.
     * @param collector  is the information of the collector to connect from.
     *                   If the stream can't be created will try to create a new one.
     */
    public StreamObserver<CollectorMessages> getCollectorStreamObserver(IExecuteActionOnNext toDoAction,
                                                                        IExecuteActionOnError errorAction, AuthResponse collector) throws CollectorServiceGrpcException {

        try {
            return nonBlockingStub.withInterceptors(
                    new GrpcIdInterceptor().withCollectorId(collector.getId()),
                    new GrpcKeyInterceptor().withCollectorKey(collector.getKey()),
                    new GrpcTypeInterceptor()
            ).collectorStream(getCollectorMessagesObserver(toDoAction, errorAction));
        } catch (Exception e) {
            throw new CollectorServiceGrpcException(StringUtil.formatError(e.getMessage()));
        }
    }

    /**
     * Method to get collector list.
     *
     * @param request     is the request with all the pagination and search params used to list collectors
     *                    according to those params.
     * @param internalKey is the internal key to communicate internally.
     * @throws CollectorServiceGrpcException if the action can't be performed or the request is malformed.
     */
    public ListCollectorResponse listCollector(ListRequest request, String internalKey) throws CollectorServiceGrpcException {
        try {
            return blockingStub.withInterceptors(new GrpcInternalKeyInterceptor().withInternalKey(internalKey),
                    new GrpcTypeInterceptor()).listCollector(request);
        } catch (Exception e) {
            String msg = "Error listing collectors: " + e.getMessage();
            throw new CollectorServiceGrpcException(StringUtil.formatError(msg));
        }
    }

    private StreamObserver<CollectorMessages> getCollectorMessagesObserver(IExecuteActionOnNext toDoAction,
                                                                           IExecuteActionOnError errorAction) {
        final CountDownLatch waitingLatch = new CountDownLatch(1);
        if (this.collectorMessagesObserver == null) {
            return new StreamObserver<>() {

                @Override
                public void onNext(CollectorMessages messages) {
                    toDoAction.executeOnNext(messages);
                }

                @Override
                public void onError(Throwable cause) {
                    errorAction.executeOnError(cause.getMessage());
                    try {
                        waitingLatch.await(30, TimeUnit.SECONDS); // Wait for a second before reconnect

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onCompleted() {
                    logger.info("Configuration received successfully.");
                }
            };
        } else return this.collectorMessagesObserver;
    }
}

