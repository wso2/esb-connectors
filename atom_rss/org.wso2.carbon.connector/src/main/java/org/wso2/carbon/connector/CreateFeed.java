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

import java.util.Date;
import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Create the Feeds
 */
public class CreateFeed extends AbstractConnector {
    static ConnectException connectException;
    static Abdera abdera;
    static AbderaClient abderaClient;
    static Factory factory;
    static Entry entry;
    static RequestOptions opts;
    static ClientResponse resp;
    static ResponceESB responce;
    static Object Title;
    static Object Content;
    static Object HostAddress;
    static Object Author;
    static Object FeedID;
    static OMFactory OMfactory;
    static OMNamespace ns;
    static OMElement result;
    static OMElement messageElement;

    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            HostAddress = getParameter(messageContext, "HostAddress");
            Title = getParameter(messageContext, "Title");
            Content = getParameter(messageContext, "Content");
            Author = getParameter(messageContext, "Author");
            FeedID = getParameter(messageContext, "FeedID");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (HostAddress == null) {
            String errorLog = "No Host found";
            log.error(errorLog);
            connectException = new ConnectException(errorLog);
            handleException(connectException.getMessage(), connectException, messageContext);
        }
        try {
            abdera = new Abdera();
            abderaClient = new AbderaClient(abdera);
            factory = abdera.getFactory();
            entry = factory.newEntry();
            entry.setId(FeedID.toString());
            entry.setTitle(Title.toString());
            entry.setUpdated(new Date());
            entry.addAuthor(Author.toString());
            entry.setContent(Content.toString());
            opts = new RequestOptions();
            opts.setContentType("application/atom+xml;type=entry");
            try {
                resp = abderaClient.post(HostAddress.toString(), entry, opts);
                responce = new ResponceESB();
                responce.InjectMessage(messageContext, resp.getStatusText());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } catch (Exception e) {
            // log.error(e.getMessage());
            throw new ConnectException(e);
        }
    }
}