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
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import javax.xml.namespace.QName;
import java.util.Iterator;

public class ISO8583MessageProducer extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            String host = (String)messageContext.getProperty(ISO8583Constant.HOST);
            int  port = Integer.parseInt((String)messageContext.getProperty(ISO8583Constant.PORT));
            packedISO8583Message(messageContext,host,port);
        } catch (NumberFormatException e) {
            handleException("The port number does not contain a parsable integer", e);
        }
    }
    public void packedISO8583Message(MessageContext msgContext,String host,int port) {
        try {
            ISOPackager packager = ISO8583PackagerFactory.getPackager();
            SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
            OMElement getElements = soapEnvelope.getBody().getFirstElement();
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            Iterator fields = getElements.getFirstChildWithName(
                    new QName(ISO8583Constant.TAG_DATA)).getChildrenWithLocalName(ISO8583Constant.TAG_FIELD);
                while (fields.hasNext()) {
                    OMElement element = (OMElement) fields.next();
                    String getValue = element.getText();
                    try {
                        int fieldID = Integer.parseInt(element.getAttribute(
                                new QName(ISO8583Constant.TAG_ID)).getAttributeValue());
                        isoMsg.set(fieldID, getValue);
                    } catch (NumberFormatException e) {
                        log.error("The fieldID does not contain a parsable integer" + e.getMessage(), e);
                    }
                }
            byte[] data = isoMsg.pack();
            String packedMessage = new String(data).toUpperCase();
            sendMessage(msgContext,packedMessage,host,port);
        } catch(ISOException e) {
            handleException("Couldn't packed ISO8583 Messages", e);
        }
    }

    public void sendMessage(MessageContext msgContext,String isoMessage, String host, int port) {
        new ISO8583MessageHandler(msgContext,isoMessage, host, port);
    }

    private void handleException(String msg, Exception ex) {
        log.error(msg, ex);
        throw new SynapseException(ex);
    }
}
