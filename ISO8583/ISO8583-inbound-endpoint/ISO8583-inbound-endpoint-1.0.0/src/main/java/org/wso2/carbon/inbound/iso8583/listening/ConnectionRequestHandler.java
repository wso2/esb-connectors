/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
    public InboundProcessorParams params;
    private Socket connection;
    private String onErrorSeq;
    boolean sequential;
    private SynapseEnvironment synapseEnvironment;
    private String injectingSeq;
    private ISOPackager packager;
    private ISO8583MessageInject msgInject;
    private DataInputStream inputStreamReader;

    public ConnectionRequestHandler(Socket connection, InboundProcessorParams params) throws ISOException {
        try {
            this.connection = connection;
            this.injectingSeq = params.getInjectingSeq();
            this.onErrorSeq = params.getOnErrorSeq();
            this.synapseEnvironment = params.getSynapseEnvironment();
            this.params = params;
            this.packager = ISO8583PackagerFactory.getPackager();
            this.msgInject = new ISO8583MessageInject(injectingSeq, onErrorSeq, sequential,
                    synapseEnvironment);
            this.inputStreamReader = new DataInputStream(connection.getInputStream());
        }catch(IOException e){
            log.error("Couldn't read the input streams ", e);
        }
    }

    public void run() {
        log.info("There is a Client connected to socket: " + connection.toString());
            try {
                while (connection.getInputStream() != null) {
                    String fromClient = inputStreamReader.readUTF();
                    unpackRequest(fromClient);
                }
            } catch (IOException e) {
                log.error("Client may be disconnect the connection",e);
            } finally {
                try {
                    inputStreamReader.close();
                    connection.close();
                } catch (IOException e) {
                    log.error("Couldn't close I/O streams", e);
                }
            }
    }

    public void unpackRequest(String message) {
        try {
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            injectISO8583Message(isoMsg);
        }
        catch (ISOException e) {
            log.info(message);
            log.error("Couldn't unpack the message", e);
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
