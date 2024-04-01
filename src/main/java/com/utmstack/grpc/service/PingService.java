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

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle the collector ping operations
 */
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
     * Method to make a continuous ping from a collector, this is a blocking method, so, use it in separate thread
     *
     * @param request    is the ping request to the server
     * @param timeUnit   is the unit of time to wait before the next call
     * @param timeAmount the amount of timeUnit to wait before the next call
     *                   Example: timeUnit=TimeUnit.SECONDS, timeAmount=10; wait=10 seconds before the next ping call
     */
    public void ping(PingRequest request, TimeUnit timeUnit, int timeAmount) throws PingException {
        final String ctx = CLASSNAME + ".ping";
        final CountDownLatch finishLatch = new CountDownLatch(1);
        while (true) {
            try {
                StreamObserver<PingRequest> pingCall = getPingRequestStreamObserver();
                pingCall.onNext(request);
                finishLatch.await(timeAmount, timeUnit);
            } catch (PingException e) {
                throw new PingException(ctx + ": " + e.getMessage());
            } catch (InterruptedException e) {
                String msg = ctx + ": Ping process was interrupted: " + e.getMessage();
                logger.error(msg);
                throw new PingException(msg);
            }
        }
    }

    /**
     * Method to ping from a collector
     */
    private StreamObserver<PingRequest> getPingRequestStreamObserver() throws PingException {
        final String ctx = CLASSNAME + ".getPingRequestStreamObserver";
        if(pingRequestStreamObserver == null) {
            try {
                pingRequestStreamObserver = nonBlockingStub.ping(new StreamObserver<>() {

                    @Override
                    public void onNext(PingResponse pingResponse) {
                        logger.info("Response executed ..." + pingResponse.getReceived());
                    }

                    @Override
                    public void onError(Throwable cause) {
                        logger.error(ctx + ": Executing ping request to server: " + cause.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("Stream completed");
                    }
                });
            } catch (Exception e) {
                throw new PingException(ctx + ": " + e.getMessage());
            }
        }
        return pingRequestStreamObserver;
    }
}
