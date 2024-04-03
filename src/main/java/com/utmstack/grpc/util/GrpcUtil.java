package com.utmstack.grpc.util;

import com.utmstack.grpc.exception.GrpcOnCompleteException;
import io.grpc.stub.StreamObserver;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle useful functions related to gRPC
 */
public class GrpcUtil {
    private static final String CLASSNAME = "GrpcUtil";
    /**
     * Method to done making requests to server by the specific stream observer
     */
    public static void callOnCompleted(StreamObserver<?> observer) throws GrpcOnCompleteException {
        final String ctx = CLASSNAME + ".callOnCompleted";
        try {
            observer.onCompleted();
        } catch (Exception e) {
            throw new GrpcOnCompleteException(ctx + ": " + e.getMessage());
        }
    }
}
