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

package org.wso2.carbon.connector.integration.test.search;

import org.json.JSONObject;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;

public class SearchIntegrationTest extends FeedlyConnectorIntegrationTest {


    /**
     * test case for searchFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 15,enabled=true, description = "Feedly {searchFeeds} integration test.")
    public void testSearchFeeds() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_searchFeeds.txt";
        String methodName = "feedly_searchFeeds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, feedlyConnectorProperties.getProperty("q"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(response.has("results"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for searchFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 16,enabled=true, description = "Feedly {searchFeedsNegative} integration test.")
    public void testSearchFeedsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_searchFeeds.txt";
        String methodName = "feedly_searchFeeds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, "");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(response == 400);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional test case for searchFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 17,enabled=true, description = "Feedly {searchFeedsNegativeOptional} integration test.")
    public void testSearchFeedsNegativeOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_searchFeedsOptional.txt";
        String methodName = "feedly_searchFeeds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("q"),
                feedlyConnectorProperties.getProperty("locale"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
