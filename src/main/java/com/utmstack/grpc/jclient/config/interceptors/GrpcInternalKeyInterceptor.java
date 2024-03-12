package com.utmstack.grpc.jclient.config.interceptors;

import com.utmstack.grpc.jclient.config.Constants;
import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create the interceptor for internal-key header
 * */
public class GrpcInternalKeyInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> INTERNAL_KEY_HEADER =
            Metadata.Key.of(Constants.AGENT_MANAGER_INTERNAL_KEY_HEADER, Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(INTERNAL_KEY_HEADER, KeyStore.getInternalKey());
                super.start(responseListener, headers);
            }
        };
    }
}
