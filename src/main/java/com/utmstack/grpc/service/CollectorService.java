package com.utmstack.grpc.service;

import agent.CollectorOuterClass.CollectorConfig;
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
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.exception.PingException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcIdInterceptor;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcKeyInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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


    public CollectorService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorServiceGrpc.newStub(grpcManagedChannel);
        this.blockingStub = CollectorServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to register a collector
     *
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
     *
     * @param request     is th information of the collector to delete
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
     * Method to initialize the collector messages stream between client and the server.
     * If the stream can't be created will try to create a new one
     * The management
     */
    public StreamObserver<CollectorMessages> getCollectorStreamObserver(IExecuteActionOnNext toDoAction) throws CollectorServiceGrpcException {
        final String ctx = CLASSNAME + ".getCollectorStreamObserver";
        final CountDownLatch waitingLatch = new CountDownLatch(1);
        try {
            return nonBlockingStub.collectorStream(new StreamObserver<>() {

                @Override
                public void onNext(CollectorMessages messages) {
                    toDoAction.executeOnNext(messages);
                }

                @Override
                public void onError(Throwable cause) {
                    logger.error(ctx + ": Creating the receiver stream, server responded with error: " + cause.getMessage());
                    try {
                        waitingLatch.await(10, TimeUnit.SECONDS); // Wait for a second before reconnect
                        getCollectorStreamObserver(toDoAction); // Try to reconnect again
                    } catch (CollectorServiceGrpcException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onCompleted() {
                    logger.info(ctx + ": Executed successfully.");
                }
            });
        } catch (Exception e) {
            throw new CollectorServiceGrpcException(ctx + ": " + e.getMessage());
        }
    }

    /**
     * Method used to insert/update a configuration in the list of configurations.
     *
     * @param message is the instance of CollectorMessages to check if the CollectorConfig configuration is present.
     */
    public static List<CollectorConfig> upsertConfiguration(List<CollectorConfig> baseList, CollectorMessages message) {
        CollectorConfig current = message.getConfig();
        Optional<CollectorConfig> search = baseList.stream()
                .filter(c -> c.getCollectorKey().equals(current.getCollectorKey())).findFirst();

        if (search.isPresent()) {
            baseList.remove(search.get());
            baseList.add(current);
        } else {
            baseList.add(current);
        }
        return baseList;
    }

    /**
     * Method to get the list of CollectorConfig in case that has more than one received from the gRPC server
     */
    public static List<CollectorConfig> getConfigurationsByCollector(List<CollectorConfig> baseList, String collectorKey) {
        return baseList.stream().filter(c -> c.getCollectorKey().equals(collectorKey)).collect(Collectors.toList());
    }

    /**
     * Method to get collector list
     *
     * @param request is the request with all the pagination and search params used to list collectors
     *                according to those params
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

// ----------------------------------------------------------------------------------------------------------------------------------

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
}

