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

public class DeleteFeed extends AbstractConnector {
	ConnectException connectException;
	Abdera abdera;
	AbderaClient abderaClient;
	Factory factory;
	Entry entry;
	RequestOptions opts;
	ClientResponse resp;
	ResponceESB responce;

	Document<Entry> doc;
	String entryUri;

	private Object HostAddress;
	private Object EntryID;

	@Override
	public void connect(MessageContext messageContext) throws ConnectException {
		EntryID = getParameter(messageContext, "EntryID");
		HostAddress = getParameter(messageContext, "HostAddress");

		abdera = new Abdera();
		abderaClient = new AbderaClient(abdera);
		// Delete the entry. Again, we need to make sure that we have the
		// current
		// edit link for the entry
		entryUri = HostAddress.toString() + "/" + EntryID.toString() + "-";
		log.info(entryUri);

		opts = new RequestOptions();
		opts.setContentType("application/atom+xml;type=entry");

		if (entryUri != null) {
			try {
				resp = abderaClient.delete(entryUri.toString());
				responce = new ResponceESB();
				responce.InjectMessage(messageContext, resp.getStatusText());
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			log.error("The Entry cannot be Null");
			return;
		}
	}
}
