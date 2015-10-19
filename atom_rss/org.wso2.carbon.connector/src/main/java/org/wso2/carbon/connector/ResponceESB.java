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
package org.wso2.carbon.connector;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.synapse.MessageContext;

/**
 * Sent Responce to ESB
 */
public class ResponceESB {
    static OMFactory OMfactory;
    static OMNamespace ns;
    static OMElement result;
    static OMElement messageElement;

    public void InjectMessage(MessageContext messageContext, String Text) {
        OMfactory = OMAbstractFactory.getOMFactory();
        ns = OMfactory.createOMNamespace("status", "ns");
        result = OMfactory.createOMElement("result", ns);
        messageElement = OMfactory.createOMElement("Result", ns);
        messageElement.setText(Text);
        result.addChild(messageElement);
        messageContext.getEnvelope().getBody().addChild(result);
    }
}
