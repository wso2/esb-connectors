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

package org.wso2.carbon.connector.ISO8583;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.wso2.carbon.connector.core.*;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ISO8583MessageProducer extends AbstractConnector {
    private String host;
    private int port;
    private int noOfFixedThreads = 2;

    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            host = messageContext.getProperty("uri.var.serverHost").toString();
            port = Integer.parseInt(messageContext.getProperty("uri.var.serverPort").toString());
            toAxis2MessageContext(messageContext);
            } catch (Exception e) {
           handleException("error", messageContext);
        }
    }
    /**
     * Get the messages from the message context and convert the messages
     */
    private void toAxis2MessageContext(MessageContext messageContext) throws AxisFault {
        Axis2MessageContext axisMsgContext = (Axis2MessageContext) messageContext;
        org.apache.axis2.context.MessageContext msgContext = axisMsgContext
                .getAxis2MessageContext();
        try {
            packedISO8583Message(msgContext);
        } catch (ISOException e) {
           log.error("Error occurred while packed ISO8583 Messages");
        }
    }

    public void packedISO8583Message(org.apache.axis2.context.MessageContext msgContext) throws ISOException, AxisFault {
        ISOPackager packager = new GenericPackager("jposdef.xml");
        SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
        OMElement getElements = soapEnvelope.getBody().getFirstElement();
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        @SuppressWarnings("unchecked")
        Iterator<OMElement> fields = getElements.getFirstChildWithName(
                new QName(ISO8583Constant.TAG_DATA)).getChildrenWithLocalName(ISO8583Constant.TAG_FIELD);
        try {
            while (fields .hasNext()) {
                OMElement element =  fields .next();
                String getValue = element.getText();
                int fieldID = Integer.parseInt(element.getAttribute(
                        new QName("id")).getAttributeValue());
                isoMsg.set(fieldID, getValue);
            }
        }
        catch (Exception e) {
            log.error("Error while get the fields ",e);
        }
        byte[] data = isoMsg.pack();
        String packedMessage = new String(data).toUpperCase();
        log.info("Packed Message is :" + packedMessage);
        sendMessage(packedMessage);
    }

    public void sendMessage(String message){
        ExecutorService threadConnectionPool =
                Executors.newFixedThreadPool(noOfFixedThreads, Executors.defaultThreadFactory());
            threadConnectionPool.execute(new ISO8583SocketPool(host, port, message ));
    }
}
