package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise grpc connection exceptions
 * */
public class GrpcConnectionException extends Exception {
    public GrpcConnectionException(String message) {
        super(message);
    }
}
