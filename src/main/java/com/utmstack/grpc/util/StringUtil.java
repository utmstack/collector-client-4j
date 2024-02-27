package com.utmstack.grpc.util;

public class StringUtil {

    public static boolean hasText(String str) {
        return (str != null && !str.trim().isEmpty());
    }
}
