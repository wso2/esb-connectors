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

package org.wso2.carbon.connector.ISO8583;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ISO8583MessageHandler  {
    private static final Log log = LogFactory.getLog(ISO8583MessageHandler.class);
    public ISO8583MessageHandler(MessageContext messageContext,String details,String host, int port) {
        try {
            Socket socket = new Socket(host,port);
            clientHandler(messageContext,socket,details);
        } catch (IOException e) {
            handleException ("Couldn't create Socket" , e);
        }
    }
    public void clientHandler(MessageContext messageContext,Socket connection,String isoMessage) {
        DataOutputStream outStream = null;
        BufferedReader inFromServer = null;
        String message;
        try {
            outStream = new DataOutputStream(connection.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                if (connection.isConnected()) {
                    outStream.writeUTF(isoMessage);
                    outStream.flush();

                /* Sender will receive the Acknowledgement here */
                    if ((message = inFromServer.readLine())!= null) {
                        unpackResponse(messageContext,message);
                    }
                }
        } catch (IOException e) {
            handleException("An exception occurred in sending the ISO8583 message", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inFromServer != null) {
                    inFromServer.close();
                }
                connection.close();
            } catch (IOException e) {
                log.error("Couldn't close the I/O Streams", e);
            }
        }
    }

    public void unpackResponse(MessageContext messageContext,String message) {
        try {
            ISOPackager packager = ISO8583PackagerFactory.getPackager();
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            messageBuilder(messageContext,isoMsg);
        } catch (ISOException e) {
            log.error("Couldn't unpack the message since message is not in ISO Standard :" + message ,e);
        }
    }

    public void messageBuilder(MessageContext messageContext,ISOMsg isomsg) {
        OMFactory OMfactory = OMAbstractFactory.getOMFactory();
        OMElement parentElement = OMfactory.createOMElement(ISO8583Constant.TAG_MSG, null);
        OMElement result = OMfactory.createOMElement(ISO8583Constant.TAG_DATA, null);
        for (int i = 0; i <= isomsg.getMaxField(); i++) {
            if (isomsg.hasField(i)) {
                String outputResult = isomsg.getString(i);
                OMElement messageElement = OMfactory.createOMElement(ISO8583Constant.TAG_FIELD, null);
                messageElement.addAttribute(OMfactory.createOMAttribute(ISO8583Constant.TAG_ID, null, String.valueOf(i)));
                messageElement.setText(outputResult);
                result.addChild(messageElement);
                parentElement.addChild(result);
                messageContext.getEnvelope().getBody().addChild(parentElement);
            }
        }
    }

    private void handleException(String msg, Exception ex) {
        log.error(msg, ex);
        throw new SynapseException(ex);
    }
}
