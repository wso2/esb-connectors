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
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.lang.Exception;

/**
 * Edit Existing Feed By ID
 */
public class EditFeed extends AbstractConnector {
    private static Document<Entry> doc;

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String entryID = getParameter(messageContext, FeedConstant.entryID).toString();
        String hostAddress = getParameter(messageContext, FeedConstant.hostAddress).toString();
        String title = getParameter(messageContext, FeedConstant.title).toString();
        String content = getParameter(messageContext, FeedConstant.content).toString();
        String author = getParameter(messageContext, FeedConstant.author).toString();

        if (entryID == null || hostAddress == null) {
            log.error("EntryID && HostAddress Can not be Empty");
        }
        String entryUri = hostAddress + "/" + entryID + "-";
        Abdera abdera = new Abdera();
        AbderaClient abderaClient = new AbderaClient(abdera);
        try {
            // Get the Entry from Server
            doc = abderaClient.get(entryUri).getDocument();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (title != null) {
            doc.getRoot().getTitleElement().setText(title);
        }
        if (content != null) {
            doc.getRoot().getContentElement().setText(content);
        }
        if (author != null) {
            doc.getRoot().getAuthor().setText(author);
        }
        RequestOptions opts = new RequestOptions();
        opts.setContentType(FeedConstant.contentType);
        try {
            ClientResponse resp = abderaClient.put(entryUri, doc.getRoot(), opts);
            ResponseESB response = new ResponseESB();
            response.InjectMessage(messageContext, resp.getStatusText());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
