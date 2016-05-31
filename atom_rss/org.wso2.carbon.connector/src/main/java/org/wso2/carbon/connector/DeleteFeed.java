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

import org.apache.abdera.Abdera;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Delete the Existing feed by ID
 */
public class DeleteFeed extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String entryID = (String) getParameter(messageContext, FeedConstant.entryID);
        String hostAddress = (String) getParameter(messageContext, FeedConstant.hostAddress);

        if (entryID.equals("") || hostAddress.equals("")||entryID==null||hostAddress==null) {
            handleException("Entry ID and host address can not be null or empty", messageContext);
        }

        Abdera abdera = new Abdera();
        AbderaClient abderaClient = new AbderaClient(abdera);
        String entryUri = hostAddress + "/" + entryID + "-";
        if (log.isDebugEnabled()){
            log.debug("Requester entry URL is " + entryUri);
        }
        FeedFactory response = new FeedFactory();
        ClientResponse resp = null;
        try {
            resp = abderaClient.delete(entryUri);
            response.InjectMessage(messageContext, resp.getStatusText());
        } catch (Exception ex){
            handleException(resp.getStatusText(), ex, messageContext);
        }
    }
}

