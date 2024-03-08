package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer
 * This class is used to raise collector configuration exceptions
 * */
public class CollectorConfigurationGrpcException extends Exception {
    public CollectorConfigurationGrpcException(String message) {
        super(message);
    }
}
