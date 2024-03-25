package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise messaging exceptions
 * */
public class LogMessagingException extends Exception{
    public LogMessagingException(String message) {
        super(message);
    }
}
