/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class EditFeed extends AbstractConnector {
    private ConnectException connectException;
    private Abdera abdera;
    private AbderaClient abderaClient;
    private Factory factory;
    private Entry entry;
    private RequestOptions opts;
    private ClientResponse resp;
    private ResponceESB responce;
    private Object HostAddress;
    private Object EntryID;
    private Object Title;
    private Object Content;
    private Object Author;
    private Document<Entry> doc;
    private String entryUri;

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        EntryID = getParameter(messageContext, "EntryID");
        HostAddress = getParameter(messageContext, "HostAddress");
        Title = getParameter(messageContext, "Title");
        Content = getParameter(messageContext, "Content");
        Author = getParameter(messageContext, "Author");
        entryUri = HostAddress.toString() + "/" + EntryID.toString() + "-";
        abdera = new Abdera();
        abderaClient = new AbderaClient(abdera);
        try {
            // Get the Entry from Server
            doc = abderaClient.get(entryUri).getDocument();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (Title.toString() != null) {
            doc.getRoot().getTitleElement().setText(Title.toString());
        }
        if (Content.toString() != null) {
            doc.getRoot().getContentElement().setText(Content.toString());
        }
        if (Author.toString() != null) {
            doc.getRoot().getAuthor().setText(Author.toString());
        }
        opts = new RequestOptions();
        opts.setContentType("application/atom+xml;type=entry");
        try {
            resp = abderaClient.put(entryUri.toString(), doc.getRoot(), opts);
            responce = new ResponceESB();
            responce.InjectMessage(messageContext, resp.getStatusText());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
