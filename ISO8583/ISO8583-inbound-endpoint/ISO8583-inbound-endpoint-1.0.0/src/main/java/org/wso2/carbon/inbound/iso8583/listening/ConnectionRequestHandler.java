package org.wso2.carbon.inbound.iso8583.listening;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

public class ConnectionRequestHandler implements Runnable {
    private static final Log log = LogFactory.getLog(ConnectionRequestHandler.class);
    private int port;
    private String host;
    private Socket connection;
    public  DataInputStream inputstreamreader;
    private String onErrorSeq;
    private boolean sequential;
    private SynapseEnvironment synapseEnvironment;
    private String injectingSeq;
    protected InboundProcessorParams params;
    protected String name;
    ISOPackager packager;
    private ISO8583MessageInject msgInject;


    public ConnectionRequestHandler(Socket connection, InboundProcessorParams params, int port ,String host) throws ISOException {
        this.connection = connection;
        this.injectingSeq = params.getInjectingSeq();
        this.onErrorSeq = params.getOnErrorSeq();
        this.name = params.getName();
        this.synapseEnvironment = params.getSynapseEnvironment();
        this.params = params;
        this.port = port;
        this.host = host;
        this.packager = ISO8583PackagerFactory.getPackager();
        this.msgInject = new ISO8583MessageInject(injectingSeq, onErrorSeq, sequential,
                synapseEnvironment);
    }

    public void run() {
        log.info("There is a Client connected to socket: " + connection.toString());
        try {
            inputstreamreader = new DataInputStream(connection.getInputStream());
            while (connection.getInputStream() != null) {
                String fromClient = inputstreamreader.readLine();
                unpackRequest(fromClient);
            }
        } catch (IOException ioe) {
            log.error("Error while read the message", ioe);
        } catch (ISOException isoe) {
            log.error("Error while unpack the request", isoe);
        } catch (Exception e) {
            log.error("Client Disconnect the connection on port", e);
            log.info("Server is listening on port for request: "+ host + ":" + port);
        } finally {
            try {
                inputstreamreader.close();
                connection.close();
            } catch (Exception e) {
                log.error("Couldn't close I/O streams", e);
            }
        }
    }

    public void unpackRequest(String message) throws ISOException, Exception {
        try {
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            injectISO8583Message(isoMsg);
        } catch (ISOException e) {
            log.info(message);
            log.error("Message is not in ISO8583 standard", e);
        }
    }

    public ISOMsg injectISO8583Message(ISOMsg isomsg) {
        if (injectingSeq != null) {
            msgInject.inject(isomsg);
        } else {
            handleException("The Sequence is not found");
        }
        return null;
    }

    private void handleException(String msg) {
        log.error(msg);
        throw new SynapseException(msg);
    }
}
