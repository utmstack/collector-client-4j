package com.utmstack.grpc.service;

import agent.CollectorOuterClass.ConfigKnowledge;
import agent.CollectorOuterClass.CollectorConfig;
import agent.PanelCollectorServiceGrpc;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcInternalKeyInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the services related to collector configurations.
 */
public class PanelCollectorService {
    private static final String CLASSNAME = "CollectorConfigurationService";
    private static final Logger logger = LogManager.getLogger(CollectorService.class);
    private final PanelCollectorServiceGrpc.PanelCollectorServiceBlockingStub blockingStub;
    private final ManagedChannel grpcManagedChannel;

    private StreamObserver<CollectorConfig> configStreamObserver;

    public PanelCollectorService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.blockingStub = PanelCollectorServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to register a collector.
     *
     * @param config is th information of the collector to register.
     * @param internalKey is the internal key to communicate internally.
     * @throws CollectorServiceGrpcException if the action can't be performed.
     */
    public ConfigKnowledge insertCollectorConfig(CollectorConfig config, String internalKey) throws CollectorConfigurationGrpcException {
        final String ctx = CLASSNAME + ".insertCollectorConfig";
        try {
            return blockingStub.withInterceptors(new GrpcInternalKeyInterceptor().withInternalKey(internalKey)).registerCollectorConfig(config);
        } catch (Exception e) {
            String msg = ctx + ": Error inserting collector configuration: " + e.getMessage();
            logger.error(msg);
            throw new CollectorConfigurationGrpcException(msg);
        }
    }
}
