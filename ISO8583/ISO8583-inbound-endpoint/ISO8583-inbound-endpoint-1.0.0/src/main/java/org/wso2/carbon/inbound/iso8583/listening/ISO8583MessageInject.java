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

import org.apache.axiom.om.*;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.mediators.base.SequenceMediator;
import org.jpos.iso.ISOMsg;
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.apache.axiom.om.util.UUIDGenerator;


public class ISO8583MessageInject {
    private static final Log log = LogFactory.getLog(ISO8583MessageInject.class);
    private final String injectingSeq;
    private final String onErrorSeq;
    private final boolean sequential;
    private final SynapseEnvironment synapseEnvironment;
    private OMFactory OMfactory;
    private OMElement result;
    private OMElement messageElement;
    private String outputResult;
    private OMElement parentElement;

    public ISO8583MessageInject(String injectingSeq, String onErrorSeq, boolean sequential,
                                SynapseEnvironment synapseEnvironment) {
        this.injectingSeq = injectingSeq;
        this.onErrorSeq = onErrorSeq;
        this.sequential = sequential;
        this.synapseEnvironment = synapseEnvironment;
    }

    /**
     * message builder is used to set the ISO8583 messages to the
     * message context and
     * inject the message to the sequence
     */
    public boolean inject(ISOMsg object) {
        org.apache.synapse.MessageContext msgCtx = null;
        try {
            msgCtx = createMessageContext();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (injectingSeq == null || injectingSeq.equals("")) {
            log.error("Sequence name not specified. Sequence : " + injectingSeq);
            return false;
        }
        SequenceMediator seq =
                (SequenceMediator) synapseEnvironment.getSynapseConfiguration()
                        .getSequence(injectingSeq);
        try {
            seq.setErrorHandler(onErrorSeq);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            messageBuilder(object);
            msgCtx.getEnvelope().getBody().addChild(parentElement);

        } catch (Exception e) {
            log.error("Error while build the message", e);
        }
        if (seq != null) {
            if (log.isDebugEnabled()) {
                log.info("injecting message to sequence : " + injectingSeq);
            }
            synapseEnvironment.injectInbound(msgCtx, seq, sequential);
        } else {
            log.error("Sequence: " + injectingSeq + " not found");
        }
        return true;
    }

    /**
     * Create the initial message context for ISO8583
     * create a synapse environment to send ISO8583 message to Inbound.
     */
    @SuppressWarnings("deprecation")
    private org.apache.synapse.MessageContext createMessageContext() {
        org.apache.synapse.MessageContext msgCtx = synapseEnvironment.createMessageContext();
        MessageContext axis2MsgCtx =
                ((org.apache.synapse.core.axis2.Axis2MessageContext) msgCtx).getAxis2MessageContext();
        axis2MsgCtx.setServerSide(true);
        axis2MsgCtx.setMessageID(UUIDGenerator.getUUID());
        msgCtx.setProperty(MessageContext.CLIENT_API_NON_BLOCKING, true);
        PrivilegedCarbonContext carbonContext =
                PrivilegedCarbonContext.getThreadLocalCarbonContext();
        axis2MsgCtx.setProperty(MultitenantConstants.TENANT_DOMAIN, carbonContext.getTenantDomain());
        return msgCtx;
    }

    public void messageBuilder(ISOMsg isomsg) {
        OMfactory = OMAbstractFactory.getOMFactory();
        parentElement = OMfactory.createOMElement(ISO8583Constant.TAG_MSG, null);
        result = OMfactory.createOMElement(ISO8583Constant.TAG_DATA, null);
        for (int i = 0; i <= isomsg.getMaxField(); i++) {
            if (isomsg.hasField(i)) {
                outputResult = isomsg.getString(i);
                messageElement = OMfactory.createOMElement(ISO8583Constant.TAG_FIELD, null);
                messageElement.addAttribute(OMfactory.createOMAttribute("id", null , String.valueOf(i)));
                messageElement.setText(outputResult);
                result.addChild(messageElement);
                parentElement.addChild(result);
            }
        }
    }
}
