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

package org.wso2.carbon.connector.integration.test.stream;

import org.json.JSONObject;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;

public class StreamIntegrationTest extends FeedlyConnectorIntegrationTest {

    /**
     * Negative test case for getContentOfStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 46,enabled=true, description = "Feedly {getContentOfStreamNegative} integration test.")
    public void testGetContentOfStreamNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getContentOfStream.txt";
        String methodName = "feedly_getContentOfStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for getContentOfStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 45, enabled=true,description = "Feedly {getContentOfStream} integration test.")
    public void testGetContentOfStream() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getContentOfStream.txt";
        String methodName = "feedly_getContentOfStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional test case for getContentOfStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 47,enabled=true, description = "Feedly {getContentOfStreamOptional} integration test.")
    public void testGetContentOfStreamOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getContentOfStreamOptional.txt";
        String methodName = "feedly_getContentOfStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for getListOfEntriesInStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 48, enabled=true,description = "Feedly {getListOfEntriesInStream} integration test.")
    public void testGetListOfEntriesInStream() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfEntriesInStream.txt";
        String methodName = "feedly_getListOfEntriesInStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("ids"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getListOfEntriesInStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 49,enabled=true, description = "Feedly {getListOfEntriesInStreamNegative} integration test.")
    public void testGetListOfEntriesInStreamNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfEntriesInStream.txt";
        String methodName = "feedly_getListOfEntriesInStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional test case for getListOfEntriesInStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 50,enabled=true, description = "Feedly {getListOfEntriesInStreamOptional} integration test.")
    public void testGetListOfEntriesInStreamOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfEntriesInStreamOptional.txt";
        String methodName = "feedly_getListOfEntriesInStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("ids"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
