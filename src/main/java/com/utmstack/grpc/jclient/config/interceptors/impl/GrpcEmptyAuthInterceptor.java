package com.utmstack.grpc.jclient.config.interceptors.impl;

import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create the base empty interceptor as default for calls when no other is defined.
 * */
public class GrpcEmptyAuthInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // Just forward the call
                super.start(responseListener, headers);
            }
        };
    }
}
