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

package org.wso2.carbon.connector.integration.test.categories;

import org.json.JSONObject;
import org.wso2.carbon.connector.integration.test.feedly.FeedlyConnectorIntegrationTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;

import javax.activation.DataHandler;
import java.net.URL;

public class CategoriesIntegrationTest extends FeedlyConnectorIntegrationTest {

    /**
      * test case for getCategories method.
      */
    @Test(groups = { "wso2.esb" },priority = 5,enabled=true, description = "Feedly {getCategories} integration test.")
    public void testGetCategories() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getCategories.txt";
        String methodName = "feedly_getCategories";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, feedlyConnectorProperties.getProperty("accessToken"));

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
     * Negative test case for getCategories method.
     */
    @Test(groups = { "wso2.esb" },priority = 5,enabled=true, description = "Feedly {getCategoriesNegative} integration test.")
    public void testGetCategoriesNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_getCategories.txt";
        String methodName = "feedly_getCategories";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(responseHeader == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for deleteCategory method.
     */
    @Test(groups = { "wso2.esb" },priority = 7, enabled=true,description = "Feedly {deleteCategory} integration test.")
    public void testDeleteCategory() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_deleteCategory.txt";
        String methodName = "feedly_deleteCategory";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, feedlyConnectorProperties.getProperty("accessToken"));

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
     * Negative test case for deleteCategory method.
     */
    @Test(groups = { "wso2.esb" },priority = 7, enabled=true,description = "Feedly {deleteCategoryNegative} integration test.")
    public void testDeleteCategoryNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_deleteCategory.txt";
        String methodName = "feedly_deleteCategory";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString, feedlyConnectorProperties.getProperty("accessTokenNegative"));

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            Assert.assertTrue(responseHeader == 401);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * test case for changeLabelCategory method.
     */
    @Test(groups = { "wso2.esb" },priority = 6,enabled=true, description = "Feedly {changeLabelCategory} integration test.")
    public void testChangeLabelCategory() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_changeLabelCategory.txt";
        String methodName = "feedly_changeLabelCategory";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                "test",
                "test");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName),newJsonString);
            log.info("response"+responseHeader);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Negative test case for changeLabelCategory method.
     */
    @Test(groups = { "wso2.esb" },priority = 6,enabled=true, description = "Feedly {changeLabelCategoryNegative} integration test.")
    public void testChangeLabelCategoryNegative() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "feedly_changeLabelCategory.txt";
        String methodName = "feedly_changeLabelCategory";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        String newJsonString = String.format(jsonString,
                feedlyConnectorProperties.getProperty("accessToken"),
                "global.must",
                "Changed");

        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));

        try {
            JSONObject object = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), newJsonString);

            Assert.assertTrue(object.has("errorMessage"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

}
