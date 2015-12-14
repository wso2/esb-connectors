
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
package org.wso2.carbon.connector.integration.test.zoho.Invoices;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.zoho.ZohoConnectorIntegrationTest;
import javax.activation.DataHandler;
import java.net.URL;

public class InvoicesConnectorIntegrationTest extends ZohoConnectorIntegrationTest {

    /*
      mandatory parameters for getTheListOfInvoices
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getTheListOfInvoices} integration test")
    public void testListInvoicesWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfInvoices_mandatory.txt";
        String methodName = "zoho_getTheListOfInvoices";

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
            Assert.assertTrue(jsonObject.has("invoices"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /*
     optional parameters for getTheListOfInvoices
     */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getTheListOfInvoices} integration test")
    public void testListInvoicesWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfInvoices_optional.txt";
        String methodName = "zoho_getTheListOfInvoices";
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
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    //negative test case for getTheListOfInvoices
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getTheListOfInvoices} integration test")
    public void testListInvoicesWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfInvoices_negative.txt";
        String methodName = "zoho_getTheListOfInvoices";
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
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /*
     *mandatory parameters for getDetailOfAnInvoice
     */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getDetailOfAnInvoice} integration test")
    public void testGetDetailOfAnInvoiceWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfAnInvoice_mandatory.txt";
        String methodName = "zoho_getDetailOfAnInvoice";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken"),zohoConnectorProperties.getProperty("invoiceId"),zohoConnectorProperties.getProperty("accept")
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

    //optional parameters for getDetailOfAnInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getDetailOfAnInvoice} integration test")
    public void testGetDetailOfAnInvoiceWithoptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfAnInvoice_optional.txt";
        String methodName = "zoho_getDetailOfAnInvoice";
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

    //negative test case getDetailOfAnInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getDetailOfAnInvoice} integration test")
    public void testGetDetailOfAnInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfAnInvoice_negative.txt";
        String methodName = "zoho_getDetailOfAnInvoice";
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
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /*
    *mandatory parameters for createANewInvoice
    */
    @Test( priority = 1,enabled = false,groups = {"wso2.esb"}, description = "zoho {createANewInvoice} integration test")
    public void testcreateANewInvoiceWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewInvoice_mandatory.txt";
        String methodName = "zoho_createANewInvoice";
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
            String invoiceId= jsonObject.getJSONObject("invoice").getString("invoice_id");
            zohoConnectorProperties.setProperty("invoiceId",invoiceId);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    //optional parameters for createANewInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {createANewInvoice} integration test")
    public void testCreateANewInvoiceWithoptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewInvoice_optional.txt";
        String methodName = "zoho_createANewInvoice";
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

    //negative test case for createANewInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {createANewInvoice} integration test")
    public void testCreateANewInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewInvoice_negative.txt";
        String methodName = "zoho_createANewInvoice";
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
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /*
     *mandatory parameters for updateExistingInvoice
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {updateExistingInvoice} integration test")
    public void testupdateExistingInvoiceWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingInvoice_mandatory.txt";
        String methodName = "zoho_updateExistingInvoice";
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

    //optional parameters updateExistingInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {updateExistingInvoice} integration test")
    public void testUpdateExistingInvoiceWithoptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingInvoice_optional.txt";
        String methodName = "zoho_updateExistingInvoice";
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

    //negative test case updateExistingInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {updateExistingInvoice} integration test")
    public void testUpdateExistingInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingInvoice_negative.txt";
        String methodName = "zoho_updateExistingInvoice";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 405);
            JSONObject jsonObject = ConnectorIntegrationUtil.sendRequest(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /*
      mandatory parameters for emailTheInvoice
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {emailTheInvoice} integration test")
    public void testEmailTheInvoiceWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "emailTheInvoice_mandatory.txt";
        String methodName = "zoho_emailTheInvoice";
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

    //negative test case for emailTheInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {emailTheInvoice} integration test")
    public void testEmailTheInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "emailTheInvoice_negative.txt";
        String methodName = "zoho_emailTheInvoice";
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

    /*
      mandatory parameters for getContentOfAnInvoiceEmail
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getContentOfAnInvoiceEmail} integration test")
    public void testGetContentOfAnInvoiceEmailWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getContentOfAnInvoiceEmail_mandatory.txt";
        String methodName = "zoho_getContentOfAnInvoiceEmail";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken")
        );
        proxyAdmin.addProxyService(new DataHandler(new URL(proxyFilePath)));
        try {
            int responseHeader = ConnectorIntegrationUtil.sendRequestToRetriveHeaders(getProxyServiceURL(methodName), modifiedJsonString);
            Assert.assertTrue(responseHeader == 200);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    //negative test case for getContentOfAnInvoiceEmail
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getContentOfAnInvoiceEmail} integration test")
    public void testGetContentOfAnInvoiceEmailWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getContentOfAnInvoiceEmail_negative.txt";
        String methodName = "zoho_getContentOfAnInvoiceEmail";
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

    /*
      mandatory parameters for writeOffTheInvoice
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {writeOffTheInvoice} integration test")
    public void testWriteOffTheInvoiceContentWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "writeOffTheInvoice_mandatory.txt";
        String methodName = "zoho_writeOffTheInvoice";
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

    //negative test case for writeOffTheInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {writeOffTheInvoice} integration test")
    public void testWriteOffTheInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "writeOffTheInvoice_negative.txt";
        String methodName = "zoho_writeOffTheInvoice";
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

    /*
    mandatory parameters for cancelTheWriteOffAnInvoice
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {cancelTheWriteOffAnInvoice} integration test")
    public void testCancelTheWriteOffAnInvoiceContentWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "cancelTheWriteOffAnInvoice_mandatory.txt";
        String methodName = "zoho_cancelTheWriteOffAnInvoice";
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

    //negative test case for cancelTheWriteOffAnInvoice
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {cancelTheWriteOffAnInvoice} integration test")
    public void testCancelTheWriteOffAnInvoiceWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "cancelTheWriteOffAnInvoice_negative.txt";
        String methodName = "zoho_cancelTheWriteOffAnInvoice";
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

    /*
     *mandatory parameters for Update billing address
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {updateExistingInvoiceBillingAddress} integration test")
    public void testUpdateExistingInvoiceBillingAddressContentWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingInvoiceBillingAddress_mandatory.txt";
        String methodName = "zoho_UpdateInvoiceBillingAddress";

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


    /*
    *mandatory parameters for getlistOfInvoiceTemplates
    */
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getlistOfInvoiceTemplates} integration test")
    public void testGetListOfInvoiceTemplatesContentWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getlistOfInvoiceTemplates_mandatory.txt";
        String methodName = "zoho_getlistOfInvoiceTemplates";

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

    //negative test case for getlistOfInvoiceTemplates
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getlistOfInvoiceTemplates} integration test")
    public void testGetListOfInvoiceTemplatesWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getlistOfInvoiceTemplates_negative.txt";
        String methodName = "zoho_getlistOfInvoiceTemplates";
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
            Assert.assertTrue(jsonObject.has("code"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

//Positive test case for mandatory parameters for getTheListOfInvoicePayments
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getTheListOfInvoicePayments} integration test")
    public void testGetTheListOfInvoicePaymentsWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfInvoicePayments_mandatory.txt";
        String methodName = "zoho_getTheListOfInvoicePayments";
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

    //Negative test case for mandatory parameters for getTheListOfInvoicePayments
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getTheListOfInvoicePayments} integration test")
    public void testGetTheListOfInvoicePaymentsWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfInvoicePayments_negative.txt";
        String methodName = "zoho_getTheListOfInvoicePayments";
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
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

//Positive test case for mandatory parameters for getDetailOfCreditsApplied
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getDetailOfCreditsApplied} integration test")
    public void testGetDetailOfCreditsAppliedWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfCreditsApplied_mandatory.txt";
        String methodName = "zoho_getDetailOfCreditsApplied";
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

    //Negative test case for mandatory parameters for getDetailOfCreditsApplied
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {getDetailOfCreditsApplied} integration test")
    public void testGetDetailOfCreditsAppliedWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfCreditsApplied_negative.txt";
        String methodName = "zoho_getDetailOfCreditsApplied";
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
            Assert.assertTrue(jsonObject.has("message"));
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }


    //Positive test case for mandatory parameters for createANewCredit
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {createANewCredit} integration test")
    public void testCreateANewCreditWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewCredit_mandatory.txt";
        String methodName = "zoho_createANewCredit";
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

    //Positive test case for optional parameters for createANewCredit
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {createANewCredit} integration test")
    public void testCreateANewCreditWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewCredit_optional.txt";
        String methodName = "zoho_createANewCredit";
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

    //Negative test case for mandatory parameters for createANewCredit
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {deleteAnExistingPayment} integration test")
    public void testDeleteAnExistingPaymentWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "deleteAnExistingPayment_negative.txt";
        String methodName = "zoho_deleteAnExistingPayment";
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

    //Negative test case for mandatory parameters for createANewCredit
    @Test(enabled = false,groups = {"wso2.esb"}, description = "zoho {deleteAnExistingCredit} integration test")
    public void testDeleteAnExistingCreditWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "deleteAnExistingCredit_negative.txt";
        String methodName = "zoho_deleteAnExistingCredit";
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