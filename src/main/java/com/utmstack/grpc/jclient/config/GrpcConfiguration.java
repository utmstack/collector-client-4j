package com.utmstack.grpc.jclient.config;


import com.utmstack.grpc.exception.GrpcConfigurationException;
import com.utmstack.grpc.util.StringUtil;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.annotation.PreDestroy;


/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to create default configuration channel with connection-key header
 * Note: You need to assign a CONNECTION_KEY variable value of the KeyStore class before use this class
 * */
public class GrpcConfiguration {
    private ManagedChannel channel;

    private String serverAddress;

    private Integer serverPort;

    private ClientInterceptor baseInterceptor;

    private static final String CLASSNAME = "GrpcConfiguration";

    public GrpcConfiguration() {}

    public GrpcConfiguration withServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public GrpcConfiguration withServerPort(Integer serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public GrpcConfiguration withBaseAuthInterceptor(ClientInterceptor baseInterceptor) {
        this.baseInterceptor = baseInterceptor;
        return this;
    }

    /**
     * Method used to verify the parameters to create the channel.
     * this method needs to be used always, and before managedChannel()
     * */
    public GrpcConfiguration build() throws GrpcConfigurationException {
        final String ctx = CLASSNAME + ".build";
        if (!StringUtil.hasText(this.serverAddress)) {
            throw new GrpcConfigurationException(ctx + ": Server address can't be empty");
        }
        if (this.serverPort == 0) {
            throw new GrpcConfigurationException(ctx + ": Server port can't be 0");
        }
        if (this.baseInterceptor == null) {
            throw new GrpcConfigurationException(ctx + ": You must specify a base interceptor for all calls. Use the withBaseAuthInterceptor() method.");
        }
        return this;
    }

    /**
     * Method used to get the managed channel to execute the calls with
     * */
    public ManagedChannel managedChannel() throws GrpcConfigurationException {
        final String ctx = CLASSNAME + ".managedChannel";
        if (this.baseInterceptor == null) {
            throw new GrpcConfigurationException(ctx + ": You must specify a base interceptor for all calls. " +
                    "Use the withBaseAuthInterceptor() method, then use build() method before calling managedChannel() method.");
        }
        this.channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
            .intercept(this.baseInterceptor)
            .usePlaintext()
            .enableRetry()
            .build();
        return this.channel;
    }

    @PreDestroy
    public void shutdownChannel() {
        this.channel.shutdown();
    }
}
