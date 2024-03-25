package com.utmstack.grpc.jclient.config.interceptors.impl;

import com.utmstack.grpc.jclient.config.Constants;
import io.grpc.*;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create the interceptor for id header
 * */
public class GrpcIdInterceptor implements ClientInterceptor {
    private static final Metadata.Key<String> COLLECTOR_ID_HEADER =
            Metadata.Key.of(Constants.COLLECTOR_ID_HEADER, Metadata.ASCII_STRING_MARSHALLER);
    private int COLLECTOR_ID = 0;

    public GrpcIdInterceptor withCollectorId(int collectorId) {
        this.COLLECTOR_ID = collectorId;
        return this;
    }


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(COLLECTOR_ID_HEADER, String.valueOf(COLLECTOR_ID));
                super.start(responseListener, headers);
            }
        };
    }
}
