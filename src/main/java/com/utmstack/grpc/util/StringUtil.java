package com.utmstack.grpc.util;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle useful functions related to String operations
 */
public class StringUtil {

    public static boolean hasText(String str) {
        return (str != null && !str.trim().isEmpty());
    }

    public static String formatError(String error) {
        if (hasText(error)){
            return error.replace("UNAVAILABLE: io exception","SERVER UNAVAILABLE");
        } else return "";
    }
}
