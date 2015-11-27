package org.wso2.carbon.inbound.iso8583.listening;

import java.io.IOException;
import java.net.ServerSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;

public class ISO8583MessageConnection extends Thread {
    private static final Log log = LogFactory.getLog(ISO8583MessageConnection.class);
    private String host;
    private int port;
    private ServerSocket server;
    private String onErrorSeq;
    private boolean sequential;
    private boolean coordination;
    private SynapseEnvironment synapseEnvironment;
    private String injectingSeq;
    protected String name;
    protected InboundProcessorParams params;

    public ISO8583MessageConnection(int port,String host, InboundProcessorParams params) {
        this.port = port;
        this.host = host;
        this.injectingSeq = params.getInjectingSeq();
        this.onErrorSeq = params.getOnErrorSeq();
        this.name = params.getName();
        this.synapseEnvironment = params.getSynapseEnvironment();
        this.params = params;

    }

    public void run() {
        boolean listening = true;
        try {
            server = new ServerSocket(port);
            log.info("Server is listening on port :"+ port);
            while (listening) {
                handleClientRequest(server, params);
            }
        } catch (IOException e) {
            log.error("Could not listen on port: " + port, e);
        } catch (ISOException e) {
            log.error("ISOException", e);
        } try {
            server.close();
        } catch (IOException e) {
            log.error("Error while closing the serverSocket",e);
        }
    }

    private void handleClientRequest(ServerSocket server, InboundProcessorParams params) throws ISOException {
        try {
            new ConnectionRequestHandler(server.accept(), params,port,host).run();
        } catch (IOException e) {
            log.error("Could not establish the connection", e);
        }
    }
}