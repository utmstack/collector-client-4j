package com.utmstack.grpc.exception;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to raise ping exceptions
 * */
public class PingException extends Exception{
    public PingException(String message) {
        super(message);
    }
}
