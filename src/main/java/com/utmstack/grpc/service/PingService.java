package com.utmstack.grpc.service;

import agent.Ping.PingRequest;
import agent.Ping.PingResponse;
import agent.PingServiceGrpc;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.exception.PingException;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PingService {
    private static final String CLASSNAME = "PingGrpcService";
    private static final Logger logger = LogManager.getLogger(PingService.class);

    private PingServiceGrpc.PingServiceStub nonBlockingStub;
    private ManagedChannel grpcManagedChannel;
    private StreamObserver<PingRequest> pingRequestStreamObserver;

    public PingService(GrpcConnection grpcConnection) throws GrpcConnectionException {
        this.grpcManagedChannel = grpcConnection.getConnectionChannel();
        this.nonBlockingStub = PingServiceGrpc.newStub(grpcManagedChannel);
    }


    /**
     * Method to make a continuous ping from a collector
     */
    public void ping(PingRequest request, TimeUnit timeUnit, int timeAmount) throws PingException {
        final String ctx = CLASSNAME + ".ping";
        final CountDownLatch finishLatch = new CountDownLatch(1);
        while (true) {
            try {
                pingCall(request);
                finishLatch.await(timeAmount, timeUnit);
            } catch (PingException e) {
                throw new PingException(ctx + ": " + e.getMessage());
            } catch (InterruptedException e) {
                logger.error(ctx + ": Ping process was interrupted: " + e.getMessage());
                throw new PingException(ctx + ": Ping process was interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Method to ping from a collector
     */
    private void pingCall(PingRequest request) throws PingException {
        final String ctx = CLASSNAME + ".pingCall";
        try {
            pingRequestStreamObserver = nonBlockingStub.ping(new StreamObserver<>() {

                @Override
                public void onNext(PingResponse pingResponse) {
                    logger.info("Response executed ..." + pingResponse.getReceived());
                }

                @Override
                public void onError(Throwable cause) {
                    logger.error("Executing ping request to server: " + cause.getMessage());
                }

                @Override
                public void onCompleted() {
                    logger.info("Stream completed");
                }
            });
            pingRequestStreamObserver.onNext(request);
        } catch (Exception e) {
            logger.error(ctx + ": " + e.getMessage());
            throw new PingException(ctx + ": " + e.getMessage());
        }
    }

}
