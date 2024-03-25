package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise grpc connection exceptions
 * */
public class GrpcConfigurationException extends Exception {
    public GrpcConfigurationException(String message) {
        super(message);
    }
}
