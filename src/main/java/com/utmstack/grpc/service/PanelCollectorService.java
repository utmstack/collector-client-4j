package com.utmstack.grpc.service;

import agent.CollectorOuterClass.ConfigKnowledge;
import agent.CollectorOuterClass.CollectorConfig;
import agent.PanelCollectorServiceGrpc;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcInternalKeyInterceptor;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcTypeInterceptor;
import com.utmstack.grpc.util.StringUtil;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the services related to collector configurations.
 */
public class PanelCollectorService {
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
        try {
            return blockingStub.withInterceptors(new GrpcInternalKeyInterceptor().withInternalKey(internalKey),
                    new GrpcTypeInterceptor()).registerCollectorConfig(config);
        } catch (Exception e) {
            String msg = "Error inserting collector configuration: " + e.getMessage();
            throw new CollectorConfigurationGrpcException(StringUtil.formatError(msg));
        }
    }
}
