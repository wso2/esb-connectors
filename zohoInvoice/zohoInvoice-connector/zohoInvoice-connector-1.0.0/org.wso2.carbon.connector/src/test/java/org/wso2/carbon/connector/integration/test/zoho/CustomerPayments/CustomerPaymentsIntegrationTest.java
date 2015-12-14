/**
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.

 WSO2 Inc. licenses this file to you under the Apache License,
 Version 2.0 (the "License"); you may not use this file except
 in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
package org.wso2.carbon.connector.integration.test.zoho.CustomerPayments;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.zoho.ZohoConnectorIntegrationTest;
import javax.activation.DataHandler;
import java.net.URL;
public class CustomerPaymentsIntegrationTest extends ZohoConnectorIntegrationTest {

    /**
     * Positive test case for getListOfCustomerPayments method with mandatory parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {getlistOfCustomerPayments} integration test")
    public void testGetListOfCustomerPaymentsWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getlistOfCustomerPayments_mandatory.txt";
        String methodName = "zoho_getlistOfCustomerPayments";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 200);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Positive test case for getListOfCustomerPayments method with optional parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {getlistOfCustomerPayments} integration test")
    public void testGetListOfCustomerPaymentsWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getlistOfCustomerPayments_optional.txt";
        String methodName = "zoho_getlistOfCustomerPayments";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 200);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    /**
     * Positive test case for getListOfCustomerPayments method with negative parameters.
     */
    @Test(enabled = false   , groups = {"wso2.esb"}, description = "zoho {getlistOfCustomerPayments} integration test")
    public void testGetListOfCustomerPaymentsWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getlistOfCustomerPayments_negative.txt";
        String methodName = "zoho_getlistOfCustomerPayments";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 401);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for getDetailOfACustomerPayments method with mandatory parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {getDetailOfACustomerPayments} integration test")
    public void testGetDetailOfACustomerPaymentsWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfACustomerPayments_mandatory.txt";
        String methodName = "zoho_getDetailOfACustomerPayments";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 200);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for getDetailOfACustomerPayments method with negative parameters.
     */
    @Test(enabled = false   , groups = {"wso2.esb"}, description = "zoho {getDetailOfACustomerPayments} integration test")
    public void testGetDetailOfACustomerPaymentsWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfACustomerPayments_negative.txt";
        String methodName = "zoho_getDetailOfACustomerPayments";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 401);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for createANewCustomerPayment method with mandatory parameters.
     */
    @Test(enabled = true, priority = 1, groups = {"wso2.esb"}, description = "zoho {createANewCustomerPayment} integration test")
    public void testcreateANewCustomerPaymentWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewCustomerPayment_mandatory.txt";
        String methodName = "zoho_createANewCustomerPayment";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 201);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
            String paymentId= jsonObject.getJSONObject("payment").getString("payment_id");
            zohoConnectorProperties.setProperty("paymentId",paymentId);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for createANewCustomerPayment method with optional parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {createANewCustomerPayment} integration test")
    public void testcreateANewCustomerPaymentWithOptionalParameters() throws Exception {
            String jsonRequestFilePath = pathToRequestsDirectory + "createANewCustomerPayment_optional.txt";
        String methodName = "zoho_createANewCustomerPayment";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 201);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for createANewCustomerPayment method with negative parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {createANewCustomerPayment} integration test")
    public void testcreateANewCustomerPaymentWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewCustomerPayment_negative.txt";
        String methodName = "zoho_createANewCustomerPayment";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 400);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for deleteAnExisistingCustomerPayment method with mandatory parameters.
     */
    @Test(enabled = true, priority = 2,groups = {"wso2.esb"}, description = "zoho {deleteAnExisistingCustomerPayment} integration test")
    public void testdeleteAnExisistingCustomerPaymentWithRequiredParameters() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deleteAnExisistingCustomerPayment_mandatory.txt";
        String methodName = "zoho_deleteAnExisistingCustomerPayment";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken"), zohoConnectorProperties.getProperty("paymentId")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for deleteAnExisistingCustomerPayment method with negative parameters.
     */
    @Test(enabled = true, groups = {"wso2.esb"}, description = "zoho {deleteAnExisistingCustomerPayment} integration test")
    public void testdeleteAnExisistingCustomerPaymentWithNegativeParameters() throws Exception {

        String jsonRequestFilePath = pathToRequestsDirectory + "deleteAnExisistingCustomerPayment_negative.txt";
        String methodName = "zoho_deleteAnExisistingCustomerPayment";

        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 404);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }
}
