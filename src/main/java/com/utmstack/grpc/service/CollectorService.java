package com.utmstack.grpc.service;

import agent.CollectorOuterClass;
import agent.CollectorOuterClass.RegisterRequest;
import agent.CollectorOuterClass.CollectorResponse;
import agent.CollectorOuterClass.CollectorDelete;
import agent.CollectorOuterClass.ListCollectorResponse;
import agent.CollectorOuterClass.CollectorMessages;
import agent.CollectorOuterClass.CollectorHostnames;
import agent.CollectorOuterClass.FilterByHostAndModule;
import agent.CollectorServiceGrpc;
import agent.Common.ListRequest;
import agent.Common.AuthResponse;
import agent.Ping;
import com.google.protobuf.Descriptors;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.exception.PingException;
import com.utmstack.grpc.jclient.config.interceptors.GrpcIdInterceptor;
import com.utmstack.grpc.jclient.config.interceptors.GrpcKeyInterceptor;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the crud operations of collectors
 * */
public class CollectorService {
    private static final String CLASSNAME = "CollectorService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);

    private final CollectorServiceGrpc.CollectorServiceBlockingStub blockingStub;
    private final CollectorServiceGrpc.CollectorServiceStub nonBlockingStub;
    private final ManagedChannel grpcManagedChannel;

    StreamObserver<CollectorMessages> requestObserver;

    public CollectorService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorServiceGrpc.newStub(grpcManagedChannel);
        this.blockingStub = CollectorServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to register a collector
     * @param request is th information of the collector to register
     * @throws CollectorServiceGrpcException if the action can't be performed
     */
    public AuthResponse registerCollector(RegisterRequest request) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".registerCollector";
        try {
            return blockingStub.registerCollector(request);
        } catch (Exception e) {
            String msg = ctx + ": Error registering collector: " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }

    /**
     * Method to remove a collector
     * @param request is th information of the collector to delete
     * @param collectorId is the database id of the collector to delete
     * @throws CollectorServiceGrpcException if the action can't be performed
     */
    public CollectorResponse deleteCollector(CollectorDelete request, int collectorId) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".deleteCollector";
        try {
            return blockingStub.withInterceptors(new GrpcKeyInterceptor()
                                    .withCollectorKey(request.getCollectorKey()),
                            new GrpcIdInterceptor().withCollectorId(collectorId))
                    .deleteCollector(request);
        } catch (Exception e) {
            String msg = ctx + ": Error removing collector: " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }

    /**
     * Method to exchange messages with the server
     *
     * @param request is the CollectorMessages instance to send
     * @throws CollectorServiceGrpcException if the server don't answer the call within 60 seconds
     */
    public CollectorMessages callCollectorMessages(CollectorMessages request) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".callCollectorMessages";
        final List<CollectorMessages> collectorMessagesList = new ArrayList<>();

        // Adding first element to ensure that always return a valid element
        collectorMessagesList.add(CollectorMessages.newBuilder().build());

        // Create a latch to wait for server response
        CountDownLatch latch = new CountDownLatch(1);

        try {
            // Call the RPC method CollectorStream
            StreamObserver<CollectorMessages> responseObserver = new StreamObserver<>() {
                @Override
                public void onNext(CollectorMessages response) {
                    // Handle response from server
                    collectorMessagesList.set(0, response);
                    // Release the latch to allow sending the message
                    latch.countDown();
                }

                @Override
                public void onError(Throwable t) {
                    // Release the latch in case of error to avoid deadlock
                    latch.countDown();
                    logger.error(ctx + ": Executing call for collector messages, server responded with error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                    logger.info(ctx + ": Executed successfully.");
                }
            };

            requestObserver = nonBlockingStub.collectorStream(responseObserver);

            // Send a message to the server
            requestObserver.onNext(request);

            // Try to wait for the server response
            int counter = 12;
            while (counter > 0) {
                latch.await(5, TimeUnit.SECONDS);
                counter--;
            }

            // Verify if the server return something
            CollectorMessages result = collectorMessagesList.get(0);
            if (!result.hasAuthResponse() && !result.hasConfig() && !request.hasResult()) {
                throw new CollectorServiceGrpcException("Server not responded since last 60 seconds");
            }

            return result;
        } catch (Exception e) {
            String msg = ctx + ": " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }

    /**
     * Method to get collector list
     * @param request is the request with all the pagination and search params used to list collectors
     * according to those params
     * @throws CollectorServiceGrpcException if the action can't be performed or the request is malformed
     */
    public ListCollectorResponse listCollector(ListRequest request) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".listCollector";
        try {
            return blockingStub.listCollector(request);
        } catch (Exception e) {
            String msg = ctx + ": Error listing collectors: " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }

    /**
     * Method to List Collector by Hostnames (Not implemented by server yet)
     */
    private CollectorHostnames ListCollectorHostnames(ListRequest request) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".ListCollectorHostnames";
        try {
            return blockingStub.listCollectorHostnames(request);
        } catch (Exception e) {
            String msg = ctx + ": Error listing collectors hostnames: " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }

    /**
     * Method to get collectors by hostname and module (Not implemented by server yet)
     */
    private ListCollectorResponse GetCollectorsByHostnameAndModule(FilterByHostAndModule request) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".GetCollectorsByHostnameAndModule";
        try {
            return blockingStub.getCollectorsByHostnameAndModule(request);
        } catch (Exception e) {
            String msg = ctx + ": Error listing collectors by hostname and module: " + e.getMessage();
            logger.error(msg);
            throw new CollectorServiceGrpcException(msg);
        }
    }
    /**
     * Method to done making ping requests to server
     */
    public void callOnCompleted() throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".callOnCompleted";
        try {
            requestObserver.onCompleted();
        } catch (Exception e) {
            String msg = ctx + ": " + e.getMessage();
            throw new CollectorServiceGrpcException(msg);
        }
    }
}

