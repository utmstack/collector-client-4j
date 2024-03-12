package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise collector service exceptions
 * */
public class CollectorServiceGrpcException extends Exception {
    public CollectorServiceGrpcException(String message) {
        super(message);
    }
}
