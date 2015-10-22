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
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Create the Feeds
 */
public class CreateFeed extends AbstractConnector {

    public void connect(MessageContext messageContext) throws ConnectException {
        String hostAddress = getParameter(messageContext, FeedConstant.hostAddress).toString();
        String title = getParameter(messageContext, FeedConstant.title).toString();
        String content = getParameter(messageContext, FeedConstant.content).toString();
        String author = getParameter(messageContext, FeedConstant.author).toString();
        String feedID = getParameter(messageContext, FeedConstant.feedID).toString();

        if (hostAddress == null) {
            log.error("HostAddress Can not be empty");
            return;
        }

        Abdera abdera = new Abdera();
        AbderaClient abderaClient = new AbderaClient(abdera);
        Factory factory = abdera.getFactory();
        Entry entry = factory.newEntry();
        entry.setId(feedID);
        entry.setTitle(title);
        entry.setUpdated(new Date());
        entry.addAuthor(author);
        entry.setContent(content);
        RequestOptions opts = new RequestOptions();
        opts.setContentType(FeedConstant.contentType);

        try {
            ClientResponse resp = abderaClient.post(hostAddress, entry, opts);
            ResponseESB response = new ResponseESB();
            response.InjectMessage(messageContext, resp.getStatusText());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return;
        }
    }
}