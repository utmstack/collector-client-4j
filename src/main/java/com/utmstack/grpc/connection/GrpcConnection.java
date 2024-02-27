package com.utmstack.grpc.connection;

import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.GrpcConfiguration;
import com.utmstack.grpc.util.StringUtil;
import io.grpc.ManagedChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GrpcConnection {
    private ManagedChannel channel;
    private String AGENT_MANAGER_HOST;
    private int AGENT_MANAGER_PORT;
    private static final String CLASSNAME = "GrpcConnection";
    private static final Logger logger = LogManager.getLogger(GrpcConnection.class);

    public GrpcConnection() {
    }

    public void connectTo(String AGENT_MANAGER_HOST, int AGENT_MANAGER_PORT) throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".connectTo";
        try {
            GrpcConfiguration conf = new GrpcConfiguration()
                    .withServerAddress(AGENT_MANAGER_HOST)
                    .withServerPort(AGENT_MANAGER_PORT);
            this.AGENT_MANAGER_HOST = AGENT_MANAGER_HOST;
            this.AGENT_MANAGER_PORT = AGENT_MANAGER_PORT;
            this.channel = conf.managedChannel();
            logger.info(ctx + ": Success");
        } catch (Exception e) {
            logger.error(ctx + ": Error establishing connection");
            throw new GrpcConnectionException(ctx + ": " + e.getMessage());
        }
    }

    public ManagedChannel getConnectionChannel() throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".getConnectionChannel";
        if (this.channel != null) {
            return this.channel;
        } else {
            logger.error(ctx + ": Error, null channel, you must use .connectTo() first");
            throw new GrpcConnectionException(ctx + ": There is no connection configured, please, use connectTo() method first");
        }
    }

    public void reconnect() throws GrpcConnectionException {
        final String ctx = CLASSNAME + ".reconnect";

        if (StringUtil.hasText(this.AGENT_MANAGER_HOST) && this.AGENT_MANAGER_PORT > 0) {
            try {
                connectTo(this.AGENT_MANAGER_HOST, this.AGENT_MANAGER_PORT);
            } catch (GrpcConnectionException e) {
                logger.error(ctx + ": Error reconnecting ...");
                throw new GrpcConnectionException(ctx + ": " + e.getMessage());
            }
        } else {
            throw new GrpcConnectionException(ctx + ": There is no connection configured, please, use connectTo() method first");
        }
    }
}
