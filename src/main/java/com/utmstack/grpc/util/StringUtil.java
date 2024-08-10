package com.utmstack.grpc.util;

import com.utmstack.grpc.jclient.config.Constants;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class handle useful functions related to String operations
 */
public class StringUtil {

    public static boolean hasText(String str) {
        return (str != null && !str.trim().isEmpty());
    }
}
