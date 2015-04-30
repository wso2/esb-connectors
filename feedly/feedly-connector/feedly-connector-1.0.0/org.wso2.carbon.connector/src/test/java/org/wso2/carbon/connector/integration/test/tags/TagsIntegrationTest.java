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

package org.wso2.carbon.connector.integration.test.tags;

import org.json.JSONArray;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;

public class TagsIntegrationTest extends FeedlyConnectorIntegrationTest {

    /**
     * test case for getListOfTags method.
     */
    @Test(groups = { "wso2.esb" },priority = 51,enabled=true, description = "Feedly {getListOfTags} integration test.")
    public void testGetListOfTags() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfTags.txt";
        String methodName = "feedly_getListOfTags";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            JSONArray object = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(object.length() > 0);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for getListOfTags method.
     */
    @Test(groups = { "wso2.esb" },priority = 52,enabled=true, description = "Feedly {getListOfTagsNegative} integration test.")
    public void testGetListOfTagsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getListOfTags.txt";
        String methodName = "feedly_getListOfTags";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONArray object = ConnectorIntegrationUtil.sendRequestJSONArray(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for changeTagLabel method.
     */
    @Test(groups = { "wso2.esb" },priority = 58,enabled=true, description = "Feedly {changeTagLabel} integration test.")
    public void testChangeTagLabel() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_changeTagLabel.txt";
        String methodName = "feedly_changeTagLabel";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("tagId"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for changeTagLabel method.
     */
    @Test(groups = { "wso2.esb" },priority = 59,enabled=true, description = "Feedly {changeTagLabelNegative} integration test.")
    public void testChangeTagLabelNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_changeTagLabel.txt";
        String methodName = "feedly_changeTagLabel";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                "negative");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for deleteTags method.
     */
    @Test(groups = { "wso2.esb" },priority = 60,enabled=true, description = "Feedly {deleteTags} integration test.")
    public void testDeleteTags() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_deleteTags.txt";
        String methodName = "feedly_deleteTags";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("tagId"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Negative test case for deleteTags method.
     */
    @Test(groups = { "wso2.esb" },priority = 61, enabled=true,description = "Feedly {deleteTagsNegative} integration test.")
    public void testDeleteTagsNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_deleteTags.txt";
        String methodName = "feedly_deleteTags";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                "Negative");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for tagMultipleEntries method.
     */
    @Test(groups = { "wso2.esb" },priority = 57,enabled=true, description = "Feedly {tagMultipleEntriesNegative} integration test.")
    public void testTagMultipleEntriesNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_tagMultipleEntries.txt";
        String methodName = "feedly_tagMultipleEntries";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdOne"),
                feedlyConnectorProperties.getProperty("entryIdTwo"),
                "negative",
                "evitagen");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * test case for tagMultipleEntries method.
     */
    @Test(groups = { "wso2.esb" },priority = 56, enabled=true,description = "Feedly {tagMultipleEntries} integration test.")
    public void testTagMultipleEntries() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_tagMultipleEntries.txt";
        String methodName = "feedly_tagMultipleEntries";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdOne"),
                feedlyConnectorProperties.getProperty("entryIdTwo"),
                feedlyConnectorProperties.getProperty("tagId"),
                feedlyConnectorProperties.getProperty("tagIdTwo"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for tagExistingEntry method.
     */
    @Test(groups = { "wso2.esb" },priority = 54, enabled=true,description = "Feedly {tagExistingEntryNegative} integration test.")
    public void testTagExistingEntryNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_tagExistingEntryNegative.txt";
        String methodName = "feedly_tagExistingEntry";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessTokenNegative"),
                feedlyConnectorProperties.getProperty("entryIdOne"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for tagExistingEntry method.
     */
    @Test(groups = { "wso2.esb" },priority = 53,enabled=true, description = "Feedly {tagExistingEntry} integration test.")
    public void testTagExistingEntry() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_tagExistingEntry.txt";
        String methodName = "feedly_tagExistingEntry";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                feedlyConnectorProperties.getProperty("entryIdOne"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int response = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), newJsonString);
            //JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName),newJsonString);

            Assert.assertTrue(response == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
