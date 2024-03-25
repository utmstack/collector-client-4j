package com.utmstack.grpc.connection;

import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.GrpcConfiguration;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import io.grpc.ClientInterceptor;
import io.netty.util.internal.StringUtil;
import io.grpc.ManagedChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Freddy R. Laffita Almaguer.
 * This class is used to manage the grpc connection
 */
public class GrpcConnection {
    private ManagedChannel channel;
    private String AGENT_MANAGER_HOST;
    private int AGENT_MANAGER_PORT;

    private ClientInterceptor baseInterceptor;
    private static final String CLASSNAME = "GrpcConnection";
    private static final Logger logger = LogManager.getLogger(GrpcConnection.class);

    public GrpcConnection() {
    }

    /**
     * Method used to create the communication channel for gRPC.
     *
     * @param AGENT_MANAGER_HOST represents the host of the agent manager or collector manager to connect to.
     * @param AGENT_MANAGER_PORT represents the port of the agent manager or collector manager to connect to.
     * @param baseInterceptor represents the base interceptor used in all calls
     */
    public void createChannel(String AGENT_MANAGER_HOST, int AGENT_MANAGER_PORT, ClientInterceptor baseInterceptor) throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".createChannel";
        try {
            GrpcConfiguration conf = new GrpcConfiguration()
                    .withServerAddress(AGENT_MANAGER_HOST)
                    .withServerPort(AGENT_MANAGER_PORT)
                    .withBaseAuthInterceptor(evaluateInterceptor(baseInterceptor))
                    .build();
            this.AGENT_MANAGER_HOST = AGENT_MANAGER_HOST;
            this.AGENT_MANAGER_PORT = AGENT_MANAGER_PORT;
            this.channel = conf.managedChannel();
            logger.info(ctx + ": Success");
        } catch (Exception e) {
            logger.error(ctx + ": Error creating channel. " + e.getMessage());
            throw new GrpcConnectionException(ctx + ": Error creating channel. " + e.getMessage());
        }
    }

    /**
     * Method used to get the connection channel.
     * This method must be used after createChannel() method call.
     */
    public ManagedChannel getConnectionChannel() throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".getConnectionChannel";
        if (this.channel != null) {
            return this.channel;
        } else {
            logger.error(ctx + ": Error, null channel, you must use .createChannel() first");
            throw new GrpcConnectionException(ctx + ": There is no connection channel configured, please, use createChannel() method first");
        }
    }

    /**
     * Method used to try to create the channel again if the channel is null.
     */
    public void reconnectChannel() throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".reconnectChannel";

        if (!StringUtil.isNullOrEmpty(this.AGENT_MANAGER_HOST) && this.AGENT_MANAGER_PORT > 0
                && this.baseInterceptor != null) {
            try {
                createChannel(this.AGENT_MANAGER_HOST, this.AGENT_MANAGER_PORT, this.baseInterceptor);
            } catch (GrpcConnectionException e) {
                logger.error(ctx + ": Error reconnecting the channel ...");
                throw new GrpcConnectionException(ctx + ": " + e.getMessage());
            }
        } else {
            throw new GrpcConnectionException(ctx + ": There is no connection channel configured, please, use createChannel() method first");
        }
    }

    /**
     * Method to set the base interceptor used to make the calls
     */
    private ClientInterceptor evaluateInterceptor(ClientInterceptor interceptor) {
        return this.baseInterceptor = interceptor == null ? new GrpcEmptyAuthInterceptor() : interceptor;
    }
}
