package com.utmstack.grpc.service;

import agent.Common;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.exception.LogMessagingException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcIdInterceptor;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcKeyInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import plugins.IntegrationGrpc;
import plugins.Plugins;

import static com.utmstack.grpc.util.StringUtil.collectorKeyFormat;


/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the operation of sending messages through gRPC.
 * */
public class LogMessagingService {
    private static final String CLASSNAME = "LogMessagingService";
    private static final Logger logger = LogManager.getLogger(LogMessagingService.class);
    private final IntegrationGrpc.IntegrationStub nonBlockingStub;
    private final ManagedChannel grpcManagedChannel;


    public LogMessagingService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = IntegrationGrpc.newStub(grpcManagedChannel);
    }


    /**
     * Method to initialize the log messages stream between client and the server.
     *
     * @param toDoAction implementation of the action to execute.
     * @param collector is the information of the collector to connect from.
     */
    public StreamObserver<Plugins.Log> getLogsStreamObserver(IExecuteActionOnNext toDoAction, Common.AuthResponse collector) throws LogMessagingException {
        final String ctx = CLASSNAME + ".getLogsStreamObserver";
        try {
            return nonBlockingStub.withInterceptors(
                    new GrpcIdInterceptor().withCollectorId(collector.getId()),
                    new GrpcKeyInterceptor().withCollectorKey(collectorKeyFormat(collector.getKey()))
            ).processLog(new StreamObserver<>() {

                @Override
                public void onNext(Plugins.Ack ack) {
                    toDoAction.executeOnNext(ack);
                }

                @Override
                public void onError(Throwable cause) {
                        throw new RuntimeException("Error sending logs, server is unavailable !!!");
                }

                @Override
                public void onCompleted() {
                    logger.info(ctx + ": Executed successfully.");
                }
            });
        } catch (Exception e) {
            throw new LogMessagingException(e.getMessage());
        }
    }
}

