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

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.lang.StringUtils;
import org.apache.synapse.MessageContext;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ISO8583MessageProducer extends AbstractConnector {
    private String host;
    private int port;
    private String noOfThreads;

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            port = Integer.parseInt((String)messageContext.getProperty("uri.var.serverPort"));
        } catch (NumberFormatException e) {
            log.error("The port number does not contain a parsable integer" + e.getMessage(), e);
        }
        host = (String)messageContext.getProperty("uri.var.serverHost");
        noOfThreads = ((String) messageContext.getProperty("uri.var.noOfThreads"));
        packedISO8583Message(messageContext);
    }
    public void packedISO8583Message(MessageContext msgContext) {
        try {
            ISOPackager packager = new GenericPackager("jposdef.xml");
            SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
            OMElement getElements = soapEnvelope.getBody().getFirstElement();
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            Iterator fields = getElements.getFirstChildWithName(
                    new QName(ISO8583Constant.TAG_DATA)).getChildrenWithLocalName(ISO8583Constant.TAG_FIELD);
                while (fields.hasNext()) {
                    OMElement element = (OMElement) fields.next();
                    String getValue = element.getText();
                    int fieldID = Integer.parseInt(element.getAttribute(
                            new QName("id")).getAttributeValue());
                    isoMsg.set(fieldID, getValue);
                }
            byte[] data = isoMsg.pack();
            String packedMessage = new String(data).toUpperCase();
            sendMessage(packedMessage);
        }catch(ISOException e){
            log.error("Couldn't packed ISO8583 Messages" + e.getMessage(), e);
        }
    }

    public void sendMessage(String message){
        ExecutorService threadConnectionPool;
        try {
            if (!StringUtils.isEmpty(noOfThreads)) {
                threadConnectionPool = Executors.newFixedThreadPool(Integer.parseInt(noOfThreads));
            } else {
                threadConnectionPool = Executors.newFixedThreadPool(Integer.parseInt(ISO8583Constant.noOfThreads));
            }
            threadConnectionPool.execute(new ISO8583MessageHandler(host, port, message));
    } catch (NumberFormatException e) {
            log.error("The number of threads does not contain a parsable integer" + e.getMessage(), e);
        }
    }
}
