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

package org.wso2.carbon.connector.integration.test.feeds;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import javax.activation.DataHandler;
import java.net.URL;


public class FeedsIntegrationTest extends FeedlyConnectorIntegrationTest {

    /**
     * test case for getFeedMetadata method.
     */
    @Test(groups = { "wso2.esb" },priority = 70, enabled=true,description = "Feedly {getFeedMetadata} integration test.")
    public void testGetFeedMetadata() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getFeedMetadata.txt";
        String methodName = "feedly_getFeedMetadata";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("feedId"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(object.has("id"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getFeedMetadata method.
     */
    @Test(groups = { "wso2.esb" },priority = 71, enabled=true,description = "Feedly {getFeedMetadataNegative} integration test.")
    public void testGetFeedMetadataNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getFeedMetadata.txt";
        String methodName = "feedly_getFeedMetadata";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                ":feedID");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray object = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(object.length() == 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for getMetadataForListOfFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 72, enabled=true,description = "Feedly {getMetadataForListOfFeeds} integration test.")
    public void testGetMetadataForListOfFeeds() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getMetadataForListOfFeeds.txt";
        String methodName = "feedly_getMetadataForListOfFeeds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("feedId"),
                feedlyConnectorProperties.getProperty("feedIdTwo"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray object = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(object.length() > 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getMetadataForListOfFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 73,enabled=true, description = "Feedly {getMetadataForListOfFeedsNegative} integration test.")
    public void testGetMetadataForListOfFeedsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getMetadataForListOfFeeds.txt";
        String methodName = "feedly_getMetadataForListOfFeeds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                "asdfas",
                "fefad");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONArray object = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(object.length() == 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
