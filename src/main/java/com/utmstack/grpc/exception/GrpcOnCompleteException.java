package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise streamObserver.onComplete() call exceptions
 * */
public class GrpcOnCompleteException extends Exception{
    public GrpcOnCompleteException(String message) {
        super(message);
    }
}
