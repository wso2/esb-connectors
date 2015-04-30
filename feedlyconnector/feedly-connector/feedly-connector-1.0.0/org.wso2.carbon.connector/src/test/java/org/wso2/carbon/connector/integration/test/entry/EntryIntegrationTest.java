/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.connector.integration.test.entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import javax.activation.DataHandler;
import java.net.URL;


public class EntryIntegrationTest extends FeedlyConnectorIntegrationTest {


    /**
     * test case for getEntryContent method.
     */
    @Test(groups = { "wso2.esb" },priority = 18, enabled=true, description = "Feedly {getEntryContent} integration test.")
    public void testGetEntryContent() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEntryContent.txt";
        String methodName = "feedly_getEntryContent";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("entryIdOne"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getEntryContent method.
     */
    @Test(groups = { "wso2.esb" },priority = 18,enabled=true, description = "Feedly {getEntryContentNegative} integration test.")
    public void testGetEntryContentNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEntryContent.txt";
        String methodName = "feedly_getEntryContent";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                "afsdpiugfqhperiugbpiabv");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray response = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.length() == 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for getContentForListOfEntries method.
     */
    @Test(groups = { "wso2.esb" },priority = 19,enabled=true, description = "Feedly {getContentForListOfEntries} integration test.")
    public void testGetContentForListOfEntries() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getContentForListOfEntries.txt";
        String methodName = "feedly_getContentForListOfEntries";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("entryIdOne"),
                feedlyConnectorProperties.getProperty("entryIdTwo"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray response = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.length() > 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getContentForListOfEntries method.
     */
    @Test(groups = { "wso2.esb" },priority = 19,enabled=true, description = "Feedly {getContentForListOfEntriesNegative} integration test.")
    public void testGetContentForListOfEntriesNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getContentForListOfEntries.txt";
        String methodName = "feedly_getContentForListOfEntries";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                "sfdsfasodibgpoas;bvoasd",
                "sdpfoahdspgnas;vop;sadfsf");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray response = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.length() == 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for createAndTagEntry method.
     */
    @Test(groups = { "wso2.esb" },priority = 20,enabled=true, description = "Feedly {createAndTagEntry} integration test.")
    public void testCreateAndTagEntry() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_createAndTagEntryOptional.txt";
        String methodName = "feedly_createAndTagEntry";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for createAndTagEntry method.
     */
    @Test(groups = { "wso2.esb" },priority = 20,enabled=true, description = "Feedly {createAndTagEntryNegative} integration test.")
    public void testCreateAndTagEntryNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_createAndTagEntryOptional.txt";
        String methodName = "feedly_createAndTagEntry";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(responseHeader == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
