package com.utmstack.grpc.jclient.config.interceptors;

/**
 * @author Freddy R. Laffita Almaguer
 * This class is used to store the keys used by the interceptors
 * */
public class KeyStore {
    private static String CONNECTION_KEY = "";
    private static String INTERNAL_KEY = "";

    public static String getConnectionKey() {
        return CONNECTION_KEY;
    }

    public static String getInternalKey() {
        return INTERNAL_KEY;
    }

    public static void setConnectionKey(String connectionKey) {
        CONNECTION_KEY = connectionKey;
    }

    public static void setInternalKey(String internalKey) {
        INTERNAL_KEY = internalKey;
    }
}
