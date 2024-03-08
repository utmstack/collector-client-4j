package com.utmstack.grpc.jclient.config;

import com.utmstack.grpc.jclient.config.interceptors.KeyStore;
import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer
 * This class is used to create the interceptor for connection-key header
 * */
public class GrpcBaseAuthInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> CUSTOM_HEADER_KEY =
            Metadata.Key.of(Constants.AGENT_MANAGER_CONNECTION_KEY_HEADER, Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(CUSTOM_HEADER_KEY, KeyStore.getConnectionKey());
                super.start(responseListener, headers);
            }
        };
    }
}
