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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;

public class ConnectionRequestHandler implements Runnable {
    private static final Log log = LogFactory.getLog(ConnectionRequestHandler.class);
    private Socket connection;
    private ISOPackager packager;
    private ISO8583MessageInject msgInject;
    private DataInputStream inputStreamReader;
    private DataOutputStream outToClient;

    public ConnectionRequestHandler(Socket connection, InboundProcessorParams params)  {
        try {
            this.connection = connection;
            this.packager = ISO8583PackagerFactory.getPackager();
            this.msgInject = new ISO8583MessageInject(params);
            this.inputStreamReader = new DataInputStream(connection.getInputStream());
            this.outToClient = new DataOutputStream(connection.getOutputStream());
        } catch(IOException e){
            handleException("Couldn't read the input streams ");
        } catch (ISOException e) {
            handleException("Couldn't get the ISOPackager");
        }
    }

    public void run() {
        log.info("There is a Client connected to socket: " + connection.toString());
        try {
            if (connection.isConnected()) {
                String fromClient = inputStreamReader.readUTF();
                outToClient.writeBytes("ISOMessage from " + Thread.currentThread().getName() + " is consumed :");
                String isoMsg =  unpackRequest(fromClient);
                outToClient.writeBytes(isoMsg);
            }
        } catch (IOException e) {
            handleException("Client may be disconnect the connection");
        } finally {
            try {
                inputStreamReader.close();
                connection.close();
            } catch (IOException e) {
                log.error("Couldn't close I/O streams", e);
            }
        }
    }

    public String unpackRequest(String message) {
        String responseMessage = null;
        try {
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            msgInject.inject(isoMsg);

            /* Set the response fields */
            isoMsg.setMTI("0210");
            isoMsg.set("39", "00");
            byte[] msg = isoMsg.pack();
            responseMessage = new String(msg).toUpperCase();
        } catch (ISOException e) {
            log.error("Couldn't unpack the message since message is not in ISO Standard :" + message ,e);
        }
        return responseMessage;
    }

    private void handleException(String msg) {
        log.error(msg);
        throw new SynapseException(msg);
    }
}
