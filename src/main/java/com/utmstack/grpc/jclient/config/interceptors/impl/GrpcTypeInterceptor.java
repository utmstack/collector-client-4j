package com.utmstack.grpc.jclient.config.interceptors.impl;

import com.utmstack.grpc.jclient.config.Constants;
import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create the interceptor for type header
 * */
public class GrpcTypeInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> TYPE_HEADER =
            Metadata.Key.of(Constants.TYPE_HEADER, Metadata.ASCII_STRING_MARSHALLER);


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(TYPE_HEADER, Constants.COLLECTOR_PREFIX);
                super.start(responseListener, headers);
            }
        };
    }
}
