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

package org.wso2.carbon.connector.integration.test.mixes;

import org.json.JSONObject;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;

public class MixesIntegrationTest extends FeedlyConnectorIntegrationTest {


    /**
     * test case for getEngagingContentInCategoryStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 37,enabled=true, description = "Feedly {getEngagingContentInCategoryStream} integration test.")
    public void testGetEngagingContentInCategoryStream() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEngagingContentInCategoryStream.txt";
        String methodName = "feedly_getEngagingContentInCategoryStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("categoryIdFour"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("items"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getEngagingContentInCategoryStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 37,enabled=true, description = "Feedly {getEngagingContentInCategoryStreamNegative} integration test.")
    public void testGetEngagingContentInCategoryStreamNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEngagingContentInCategoryStream.txt";
        String methodName = "feedly_getEngagingContentInCategoryStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("categoryIdFour"));

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
     * Optional test case for getEngagingContentInCategoryStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 38, enabled=true,description = "Feedly {getEngagingContentInCategoryStreamOptional} integration test.")
    public void testGetEngagingContentInCategoryStreamOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEngagingContentInCategoryStreamOptional.txt";
        String methodName = "feedly_getEngagingContentInCategoryStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("categoryIdFour"),
                feedlyConnectorProperties.getProperty("locale"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("items"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional test case for getEngagingContentInFeedStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 36,enabled=true, description = "Feedly {getEngagingContentInFeedStreamOptional} integration test.")
    public void testGetEngagingContentInFeedStreamOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEngagingContentInFeedStreamOptional.txt";
        String methodName = "feedly_getEngagingContentInFeedStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("feedId"),
                feedlyConnectorProperties.getProperty("locale"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject responseO = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

           log.info("getEngagingContentInFeedStream "+response);
            Assert.assertTrue(responseO.has("id"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for getEngagingContentInFeedStream method.
     */
    @Test(groups = { "wso2.esb" },priority = 35,enabled=true, description = "Feedly {getEngagingContentInFeedStream} integration test.")
    public void testGetEngagingContentInFeedStream() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getEngagingContentInFeedStream.txt";
        String methodName = "feedly_getEngagingContentInFeedStream";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("feedId"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject responseObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            log.info("getEngagingContentInFeedStream "+response);
            Assert.assertTrue(responseObject.has("id"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
