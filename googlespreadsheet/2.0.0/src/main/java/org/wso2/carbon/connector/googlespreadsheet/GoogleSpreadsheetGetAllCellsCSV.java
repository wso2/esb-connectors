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
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class GoogleSpreadsheetGetAllCellsCSV extends AbstractConnector {

	public static final String WORKSHEET_NAME = "worksheetName";
	public static final String SPREADSHEET_NAME = "spreadsheetName";
	private static Log log = LogFactory
			.getLog(GoogleSpreadsheetGetAllCellsCSV.class);

	public void connect(MessageContext messageContext) throws ConnectException {
		try {
			String worksheetName = GoogleSpreadsheetUtils
					.lookupFunctionParam(messageContext, WORKSHEET_NAME);
			String spreadsheetName = GoogleSpreadsheetUtils
					.lookupFunctionParam(messageContext, SPREADSHEET_NAME);
			
			if (worksheetName == null || "".equals(worksheetName.trim())
					|| spreadsheetName == null
					|| "".equals(spreadsheetName.trim())
					) {
				log.error("Please make sure you have given a name for the new worksheet and spreadsheet");
                ConnectException connectException = new ConnectException("Please make sure you have given a name for the new spreadsheet");
                GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, connectException);
                return;
			}

			SpreadsheetService ssService = new GoogleSpreadsheetClientLoader(
					messageContext).loadSpreadsheetService();

			GoogleSpreadsheet gss = new GoogleSpreadsheet(ssService);

			SpreadsheetEntry ssEntry = gss
					.getSpreadSheetsByTitle(spreadsheetName);

            if(ssEntry != null) {

			GoogleSpreadsheetWorksheet gssWorksheet = new GoogleSpreadsheetWorksheet(
					ssService, ssEntry.getWorksheetFeedUrl());

			WorksheetEntry wsEntry = gssWorksheet
					.getWorksheetByTitle(worksheetName);

		    if(wsEntry != null) {
			GoogleSpreadsheetCellData gssData = new GoogleSpreadsheetCellData(
					ssService);

			
			List<CellEntry> resultData = gssData.getAllCells(wsEntry);
					
			int resultSize = resultData.size();

            GoogleSpreadsheetUtils.removeTransportHeaders(messageContext);

            if(messageContext.getEnvelope().getBody().getFirstElement() != null) {
                messageContext.getEnvelope().getBody().getFirstElement().detach();
            }
			
			OMFactory factory   = OMAbstractFactory.getOMFactory();
	        OMNamespace ns      = factory.createOMNamespace("http://org.wso2.esbconnectors.googlespreadsheet", "ns");
	        OMElement searchResult  = factory.createOMElement("getAllCellsResultCSV", ns);  
	        
	        OMElement result      = factory.createOMElement("result", ns); 
	        searchResult.addChild(result);
	        result.setText("true");

            OMElement data      = factory.createOMElement("data", ns);
            searchResult.addChild(data);

            int previousRow = 1;
			StringBuilder csvBuilder = new StringBuilder();
			
			for(int iterateCount=0; iterateCount < resultSize; iterateCount++)
			{
				if(resultData.get(iterateCount) != null) {
					
						if(previousRow == resultData.get(iterateCount).getCell().getRow()) {
							csvBuilder.append(resultData.get(iterateCount).getCell().getValue());
							csvBuilder.append(",");
							
						} else {
							csvBuilder.append("\n");	
							csvBuilder.append(resultData.get(iterateCount).getCell().getValue());
							csvBuilder.append(",");
						}
						previousRow = resultData.get(iterateCount).getCell().getRow();				        
				}
			}
		 	OMElement cellId      = factory.createOMElement("text", ns);        
	        data.addChild(cellId);
	        cellId.setText(csvBuilder.toString());
			messageContext.getEnvelope().getBody().addChild(searchResult);
            } else {
                ConnectException connectException = new ConnectException("Cannot retrieve the data. Worksheet with the given name is not available.");
                log.error("Error occured: " + connectException.getMessage(), connectException);
                GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, connectException);
            }
            } else {
                ConnectException connectException = new ConnectException("Cannot retrieve the data. Spreadsheet with the given name is not available.");
                log.error("Error occured: " + connectException.getMessage(), connectException);
                GoogleSpreadsheetUtils.storeErrorResponseStatus(messageContext, connectException);
            }
			

		} catch (IOException te) {
			log.error("Error occured " + te.getMessage(), te);
			GoogleSpreadsheetUtils.storeErrorResponseStatus(
					messageContext, te);
		} catch (ServiceException te) {
			log.error("Error occured " + te.getMessage(), te);
			GoogleSpreadsheetUtils.storeErrorResponseStatus(
					messageContext, te);
		} catch (Exception te) {
			log.error("Error occured " + te.getMessage(), te);
			GoogleSpreadsheetUtils.storeErrorResponseStatus(
					messageContext, te);
		}
	}
}
