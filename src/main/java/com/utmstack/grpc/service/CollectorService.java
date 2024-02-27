package com.utmstack.grpc.service;

import agent.CollectorOuterClass.RegisterRequest;
import agent.CollectorOuterClass.CollectorResponse;
import agent.CollectorOuterClass.CollectorDelete;
import agent.CollectorOuterClass.ListCollectorResponse;
import agent.CollectorServiceGrpc;
import agent.Common.ListRequest;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorGrpcServiceException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import io.grpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CollectorService {
    private static final String CLASSNAME = "CollectorService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);

    private CollectorServiceGrpc.CollectorServiceBlockingStub blockingStub;
    private CollectorServiceGrpc.CollectorServiceStub nonBlockingStub;
    private ManagedChannel grpcManagedChannel;

    public CollectorService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = CollectorServiceGrpc.newStub(grpcManagedChannel);
        this.blockingStub = CollectorServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to register a collector
     * */
    public AuthResponse registerCollector(RegisterRequest request) throws CollectorGrpcServiceException {
        final String ctx = CLASSNAME + ".registerCollector";
        try {
           return blockingStub.registerCollector(request);
        } catch (Exception e) {
            logger.error(ctx + ": Error registering collector");
            throw new CollectorGrpcServiceException(ctx + ": " + e.getMessage());
        }
    }

    /**
     * Method to remove a collector
     * */
    public CollectorResponse deleteCollector(CollectorDelete request) throws CollectorGrpcServiceException {
        final String ctx = CLASSNAME + ".deleteCollector";
        try {
            return blockingStub.deleteCollector(request);
        } catch (Exception e) {
            logger.error(ctx + ": Error removing collector");
            throw new CollectorGrpcServiceException(ctx + ": " + e.getMessage());
        }
    }

    /**
     * Method to get collector list
     * */
    public ListCollectorResponse listCollector(ListRequest request) throws CollectorGrpcServiceException {
        final String ctx = CLASSNAME + ".listCollector";
        try {
            return blockingStub.listCollector(request);
        } catch (Exception e) {
            logger.error(ctx + ": Error listing collectors");
            throw new CollectorGrpcServiceException(ctx + ": " + e.getMessage());
        }
    }
}

