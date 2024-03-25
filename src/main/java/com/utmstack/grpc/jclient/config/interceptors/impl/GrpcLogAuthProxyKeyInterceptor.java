package com.utmstack.grpc.jclient.config.interceptors.impl;

import com.utmstack.grpc.jclient.config.Constants;
import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create the interceptor for key header
 * */
public class GrpcLogAuthProxyKeyInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> LOG_AUTH_PROXY_KEY_HEADER =
            Metadata.Key.of(Constants.COLLECTOR_LOG_AUTH_PROXY_KEY_HEADER, Metadata.ASCII_STRING_MARSHALLER);

    private String LOG_AUTH_PROXY_KEY = "";

    public GrpcLogAuthProxyKeyInterceptor withLogAuthProxyCollectorKey(String collectorKey) {
        this.LOG_AUTH_PROXY_KEY = collectorKey;
        return this;
    }


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(LOG_AUTH_PROXY_KEY_HEADER, LOG_AUTH_PROXY_KEY);
                super.start(responseListener, headers);
            }
        };
    }
}
