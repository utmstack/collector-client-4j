package com.utmstack.grpc.jclient.config.interceptors;

import com.utmstack.grpc.jclient.config.Constants;
import io.grpc.*;

public class GrpcKeyInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> COLLECTOR_KEY_HEADER =
            Metadata.Key.of(Constants.COLLECTOR_KEY_HEADER, Metadata.ASCII_STRING_MARSHALLER);

    private String COLLECTOR_KEY = "";

    public GrpcKeyInterceptor withCollectorKey(String collectorKey) {
        this.COLLECTOR_KEY = collectorKey;
        return this;
    }


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(COLLECTOR_KEY_HEADER, COLLECTOR_KEY);
                super.start(responseListener, headers);
            }
        };
    }
}
