/*
 *  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.googlespreadsheet;

import java.io.IOException;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.ServiceException;

public class GoogleSpreadsheetGetAllSpreadsheets extends AbstractConnector {


	private static Log log = LogFactory
			.getLog(GoogleSpreadsheetCreateWorksheet.class);

	public void connect(MessageContext messageContext) throws ConnectException {
		try {

			SpreadsheetService ssService = new GoogleSpreadsheetClientLoader(
					messageContext).loadSpreadsheetService();

			GoogleSpreadsheet gss = new GoogleSpreadsheet(ssService);
			
			List<String> resultData = gss.getAllSpreadsheets();
           
			
			int resultSize = resultData.size();

            GoogleSpreadsheetUtils.removeTransportHeaders(messageContext);

            if(messageContext.getEnvelope().getBody().getFirstElement() != null) {
                messageContext.getEnvelope().getBody().getFirstElement().detach();
            }
			
			OMFactory factory   = OMAbstractFactory.getOMFactory();
	        OMNamespace ns      = factory.createOMNamespace("http://org.wso2.esbconnectors.googlespreadsheet", "ns");
	        OMElement searchResult  = factory.createOMElement("getAllSpreadsheetsResult", ns);        
	       
	        OMElement result      = factory.createOMElement("result", ns); 
	        searchResult.addChild(result);
	        result.setText("true");

            OMElement data      = factory.createOMElement("data", ns);
            searchResult.addChild(data);

            for(int iterateCount=0; iterateCount < resultSize; iterateCount++)
			{
				if(resultData.get(iterateCount) != null) {
					 	OMElement title      = factory.createOMElement("title", ns);        
				        data.addChild(title);
				        title.setText(resultData.get(iterateCount));
					
				}
			}
			
			messageContext.getEnvelope().getBody().addChild(searchResult);
           

		} catch (IOException te) {
			log.error("Error occured " + te.getMessage(), te);
			GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, te);
		} catch (ServiceException te) {
			log.error("Error occured " + te.getMessage(), te);
			GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, te);
		} catch (Exception te) {
            log.error("Error occured " + te.getMessage(), te);
            GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, te);
        }
	}
	
}
