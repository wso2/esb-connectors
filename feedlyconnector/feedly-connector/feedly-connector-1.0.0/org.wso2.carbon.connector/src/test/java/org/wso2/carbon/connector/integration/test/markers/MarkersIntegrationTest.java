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

package org.wso2.carbon.connector.integration.test.markers;

import org.json.JSONObject;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;


public class MarkersIntegrationTest extends FeedlyConnectorIntegrationTest {


    /**
     * test case for getListOfUnreadCount method.
     */
    @Test(groups = { "wso2.esb" },priority = 21, enabled=true,description = "Feedly {getListOfUnreadCount} integration test.")
    public void testGetListOfUnreadCount() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfUnreadCount.txt";
        String methodName = "feedly_getListOfUnreadCount";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("unreadcounts"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getListOfUnreadCount method.
     */
    @Test(groups = { "wso2.esb" },priority = 21,enabled=true, description = "Feedly {getListOfUnreadCountNegative} integration test.")
    public void testGetListOfUnreadCountNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfUnreadCount.txt";
        String methodName = "feedly_getListOfUnreadCount";

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
     * Optional test case for getListOfUnreadCount method.
     */
    @Test(groups = { "wso2.esb" },priority = 22,enabled=true, description = "Feedly {getListOfUnreadCountOptional} integration test.")
    public void testGetListOfUnreadCountOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfUnreadCountOptional.txt";
        String methodName = "feedly_getListOfUnreadCount";

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
     * test case for markArticleAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 23,enabled=true, description = "Feedly {markArticleAsRead} integration test.")
    public void testMarkArticleAsRead() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsRead.txt";
        String methodName = "feedly_markArticleAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * Negative test case for markArticleAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 23,enabled=true, description = "Feedly {markArticleAsReadNegative} integration test.")
    public void testMarkArticleAsReadNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsRead.txt";
        String methodName = "feedly_markArticleAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * test case for markArticleAsUnread method.
     */
    @Test(groups = { "wso2.esb" },priority = 24,enabled=true, description = "Feedly {markArticleAsUnread} integration test.")
    public void testMarkArticleAsUnread() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsUnread.txt";
        String methodName = "feedly_markArticleAsUnread";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * Negative test case for markArticleAsUnread method.
     */
    @Test(groups = { "wso2.esb" },priority = 24,enabled=true, description = "Feedly {markArticleAsUnreadNegative} integration test.")
    public void testMarkArticleAsUnreadNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsUnread.txt";
        String methodName = "feedly_markArticleAsUnread";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * test case for markFeedAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 25,enabled=true, description = "Feedly {markFeedAsRead} integration test.")
    public void testMarkFeedAsRead() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markFeedAsRead.txt";
        String methodName = "feedly_markFeedAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdFour"),
                feedlyConnectorProperties.getProperty("feedIdThree"));

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
     * Negative test case for markFeedAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 25,enabled=true, description = "Feedly {markFeedAsReadNegative} integration test.")
    public void testMarkFeedAsReadNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markFeedAsRead.txt";
        String methodName = "feedly_markFeedAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdFour"),
                feedlyConnectorProperties.getProperty("feedIdThree"));

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
     * test case for markCategoryAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 26,enabled=true, description = "Feedly {markCategoryAsRead} integration test.")
    public void testMarkCategoryAsRead() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markCategoryAsRead.txt";
        String methodName = "feedly_markCategoryAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdFour"),
                feedlyConnectorProperties.getProperty("categoryId"));

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
     * Negative test case for markCategoryAsRead method.
     */
    @Test(groups = { "wso2.esb" },priority = 26,enabled=true, description = "Feedly {markCategoryAsReadNegative} integration test.")
    public void testMarkCategoryAsReadNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markCategoryAsRead.txt";
        String methodName = "feedly_markCategoryAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdFour"),
                feedlyConnectorProperties.getProperty("categoryId"));

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
     * test case for undoMarkAsReadForCategories method.
     */
    @Test(groups = { "wso2.esb" },priority = 27,enabled=true, description = "Feedly {undoMarkAsReadForCategories} integration test.")
    public void testUndoMarkAsReadForCategories() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_undoMarkAsReadForCategories.txt";
        String methodName = "feedly_undoMarkAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("categoryIdTwo"),
                feedlyConnectorProperties.getProperty("categoryIdThree"));

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
     * Negative test case for undoMarkAsReadForCategories method.
     */
    @Test(groups = { "wso2.esb" },priority = 27,enabled=true, description = "Feedly {undoMarkAsReadForCategoriesNegative} integration test.")
    public void testUndoMarkAsReadForCategoriesNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_undoMarkAsReadForCategories.txt";
        String methodName = "feedly_undoMarkAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("categoryIdTwo"),
                feedlyConnectorProperties.getProperty("categoryIdThree"));

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
     * test case for undoMarkAsReadForFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 28,enabled=true, description = "Feedly {undoMarkAsReadForFeeds} integration test.")
    public void testUndoMarkAsReadForFeeds() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_undoMarkAsReadForFeeds.txt";
        String methodName = "feedly_undoMarkAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("feedIdThree"));

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
     * Negative test case for undoMarkAsReadForFeeds method.
     */
    @Test(groups = { "wso2.esb" },priority = 28,enabled=true, description = "Feedly {undoMarkAsReadForFeedsNegative} integration test.")
    public void testUndoMarkAsReadForFeedsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_undoMarkAsReadForFeeds.txt";
        String methodName = "feedly_undoMarkAsRead";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("feedIdThree"));

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
     * test case for markArticleAsSaved method.
     */
    @Test(groups = { "wso2.esb" },priority = 29,enabled=true, description = "Feedly {markArticleAsSaved} integration test.")
    public void testMarkArticleAsSaved() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsSaved.txt";
        String methodName = "feedly_markArticleAsSaved";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * Negative test case for markArticleAsSaved method.
     */
    @Test(groups = { "wso2.esb" },priority = 29,enabled=true, description = "Feedly {markArticleAsSavedNegative} integration test.")
    public void testMarkArticleAsSavedNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticleAsSaved.txt";
        String methodName = "feedly_markArticleAsSaved";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * test case for markArticlesAsUnsaved method.
     */
    @Test(groups = { "wso2.esb" },priority = 30,enabled=true, description = "Feedly {markArticlesAsUnsaved} integration test.")
    public void testMarkArticlesAsUnsaved() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticlesAsUnsaved.txt";
        String methodName = "feedly_markArticlesAsUnsaved";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * Negative test case for markArticlesAsUnsaved method.
     */
    @Test(groups = { "wso2.esb" },priority = 30,enabled=true, description = "Feedly {markArticlesAsUnsavedNegative} integration test.")
    public void testMarkArticlesAsUnsavedNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_markArticlesAsUnsaved.txt";
        String methodName = "feedly_markArticlesAsUnsaved";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdThree"),
                feedlyConnectorProperties.getProperty("entryIdFour"));

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
     * test case for getLatestReadOperations method.
     */
    @Test(groups = { "wso2.esb" },priority = 31,enabled=true, description = "Feedly {getLatestReadOperations} integration test.")
    public void testGetLatestReadOperations() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestReadOperations.txt";
        String methodName = "feedly_getLatestReadOperations";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("feeds"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getLatestReadOperations method.
     */
    @Test(groups = { "wso2.esb" },priority = 31,enabled=true, description = "Feedly {getLatestReadOperationsNegative} integration test.")
    public void testGetLatestReadOperationsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestReadOperations.txt";
        String methodName = "feedly_getLatestReadOperations";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401 );
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Optional test case for getLatestReadOperations method.
     */
    @Test(groups = { "wso2.esb" },priority = 32,enabled=true, description = "Feedly {getLatestReadOperationsOptional} integration test.")
    public void testGetLatestReadOperationsOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestReadOperationsOptional.txt";
        String methodName = "feedly_getLatestReadOperations";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("feeds"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * test case for getLatestTagEntryIds method.
     */
    @Test(groups = { "wso2.esb" },priority = 33,enabled=true, description = "Feedly {getLatestTagEntryIds} integration test.")
    public void testGetLatestTagEntryIds() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestTagEntryIds.txt";
        String methodName = "feedly_getLatestTagEntryIds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("taggedEntries"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getLatestTagEntryIds method.
     */
    @Test(groups = { "wso2.esb" },priority = 33,enabled=true, description = "Feedly {getLatestTagEntryIdsNegative} integration test.")
    public void testGetLatestTagEntryIdsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestTagEntryIds.txt";
        String methodName = "feedly_getLatestTagEntryIds";

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
     * Optional test case for getLatestTagEntryIds method.
     */
    @Test(groups = { "wso2.esb" },priority = 34,enabled=true, description = "Feedly {getLatestTagEntryIdsOptional} integration test.")
    public void testGetLatestTagEntryIdsOptional() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getLatestTagEntryIdsOptional.txt";
        String methodName = "feedly_getLatestTagEntryIds";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject response = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response.has("taggedEntries"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
