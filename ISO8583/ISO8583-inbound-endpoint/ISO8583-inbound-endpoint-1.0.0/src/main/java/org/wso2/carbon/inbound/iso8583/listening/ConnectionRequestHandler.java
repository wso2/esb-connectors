/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private Socket connection;
    private  DataInputStream inputstreamreader;
    private String onErrorSeq;
    private boolean sequential;
    private SynapseEnvironment synapseEnvironment;
    private String injectingSeq;
    private InboundProcessorParams params;
    private String name;
    private ISOPackager packager;
    private ISO8583MessageInject msgInject;


    public ConnectionRequestHandler(Socket connection, InboundProcessorParams params, int port) throws ISOException {
        this.connection = connection;
        this.injectingSeq = params.getInjectingSeq();
        this.onErrorSeq = params.getOnErrorSeq();
        this.name = params.getName();
        this.synapseEnvironment = params.getSynapseEnvironment();
        this.params = params;
        this.port = port;
        this.packager = ISO8583PackagerFactory.getPackager();
        this.msgInject = new ISO8583MessageInject(injectingSeq, onErrorSeq, sequential,
                synapseEnvironment);
    }
    @SuppressWarnings("deprecation")
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
                log.info("Server is listening on port for request: "  + ":" + port);
            } finally {
                try {
                    inputstreamreader.close();
                    connection.close();
                } catch (Exception e) {
                    log.error("Couldn't close I/O streams", e);
                }
            }
    }

    public void unpackRequest(String message) throws Exception {
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
