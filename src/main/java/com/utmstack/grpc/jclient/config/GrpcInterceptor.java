package com.utmstack.grpc.jclient.config;

import io.grpc.*;

public class GrpcInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> CUSTOM_HEADER_KEY =
            Metadata.Key.of(Constants.AGENT_MANAGER_CONNECTION_KEY_HEADER, Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(CUSTOM_HEADER_KEY, System.getenv(Constants.ENV_CONNECTION_KEY));
                super.start(responseListener, headers);
            }
        };
    }
}
