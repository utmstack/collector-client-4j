package com.utmstack.grpc.service;

import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.exception.LogMessagingException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcKeyInterceptor;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcLogAuthProxyKeyInterceptor;
import io.grpc.ManagedChannel;
import logservice.Log.ReceivedMessage;
import logservice.Log.LogMessage;
import logservice.LogServiceGrpc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the operation of sending messages through gRPC.
 * */
public class LogMessagingService {
    private static final String CLASSNAME = "LogMessagingService";
    private static final Logger logger = LogManager.getLogger(LogMessagingService.class);
    private final LogServiceGrpc.LogServiceBlockingStub blockingStub;
    private final ManagedChannel grpcManagedChannel;


    public LogMessagingService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.blockingStub = LogServiceGrpc.newBlockingStub(grpcManagedChannel);
    }


    /**
     * Method to send logs from a collector
     * @param request is th information of the messages to send
     * @param collectorKey the key of the collector that are trying to send logs
     * @throws LogMessagingException if the action can't be performed
     */
    public ReceivedMessage sendLogs(LogMessage request, String collectorKey) throws LogMessagingException {
        final String ctx = CLASSNAME + ".sendLogs";
        try {
            return blockingStub.withInterceptors(new GrpcLogAuthProxyKeyInterceptor()
                    .withLogAuthProxyCollectorKey(collectorKey)).processLogs(request);
        } catch (Exception e) {
            String msg = ctx + ": Error sending logs: " + e.getMessage();
            logger.error(msg);
            throw new LogMessagingException(msg);
        }
    }
}

