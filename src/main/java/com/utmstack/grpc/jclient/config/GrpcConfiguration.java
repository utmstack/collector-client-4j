package com.utmstack.grpc.jclient.config;


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

    public GrpcConfiguration() {}

    public GrpcConfiguration withServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public GrpcConfiguration withServerPort(Integer serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public ManagedChannel managedChannel() {
        this.channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
            .intercept(new GrpcBaseAuthInterceptor())
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
