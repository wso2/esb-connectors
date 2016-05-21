
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
package org.wso2.carbon.connector.integration.test.zoho.CreditNotes;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.connector.integration.test.zoho.ZohoConnectorIntegrationTest;

import javax.activation.DataHandler;
import java.net.URL;

public class CreditNotesConnectorIntegrationTest extends ZohoConnectorIntegrationTest {

    /**
     * Positive test case for getListOfCreditNotes method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getListOfCreditNotes} integration test")
    public void testListRefundsWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getListOfCreditNotes_mandatory.txt";
        String methodName = "zoho_getListOfCreditNotes";
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
     * Positive test case for getListOfCreditNotes method with optional parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getListOfCreditNotes} integration test")
    public void testListRefundsWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getListOfCreditNotes_optional.txt";
        String methodName = "zoho_getListOfCreditNotes";
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
     * Negative test case for getListOfCreditNotes method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getListOfCreditNotes} integration test")
    public void testListRefundsWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getListOfCreditNotes_negative.txt";
        String methodName = "zoho_getListOfCreditNotes";
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
     * Positive test case for getCreditNoteDetail method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getCreditNoteDetail} integration test")
    public void testGetACreditNoteDetailWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getACreditNoteDetail_mandatory.txt";
        String methodName = "zoho_getACreditNoteDetail";
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
     * Positive test case for getCreditNoteDetail method with optional parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getCreditNoteDetail} integration test")
    public void testGetACreditNoteDetailWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getACreditNoteDetail_optional.txt";
        String methodName = "zoho_getACreditNoteDetail";
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
     * Negative test case for getCreditNoteDetail method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getCreditNoteDetail} integration test")
    public void testGetACreditNoteDetailWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getACreditNoteDetail_negative.txt";
        String methodName = "zoho_getACreditNoteDetail";
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
     * Positive test case for createACreditNote method with mandatory parameters.
     */
    @Test(priority = 1, enabled = false, groups = {"wso2.esb"}, description = "zoho {createACreditNote} integration test")
    public void testCreateACreditNoteWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createACreditNote_mandatory.txt";
        String methodName = "zoho_createACreditNote";
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
            String creditNoteId = jsonObject.getJSONObject("creditnote").getString("creditnote_id");
            zohoConnectorProperties.setProperty("creditNoteId", creditNoteId);
        } finally {
            proxyAdmin.deleteProxy(methodName);
        }
    }

    /**
     * Positive test case for createACreditNote method with optional parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {createACreditNote} integration test")
    public void testCreateACreditNoteWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createACreditNote_optional.txt";
        String methodName = "zoho_createACreditNote";
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
     * Negative test case for createACreditNote method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {createACreditNote} integration test")
    public void testCreateACreditNoteWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createACreditNote_negative.txt";
        String methodName = "zoho_createACreditNote";
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
     * Positive test case for updateExistingCreditNote method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {updateExistingCreditNote} integration test")
    public void testUpdateExistingCreditNoteWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingCreditNote_mandatory.txt";
        String methodName = "zoho_updateExistingCreditNote";
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
     * Positive test case for updateExistingCreditNote method with optional parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {updateExistingCreditNote} integration test")
    public void testUpdateExistingCreditNoteWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingCreditNote_optional.txt";
        String methodName = "zoho_updateExistingCreditNote";
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
     * Negative test case for updateExistingCreditNote method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {updateExistingCreditNote} integration test")
    public void testUpdateExistingCreditNoteWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "updateExistingCreditNote_negative.txt";
        String methodName = "zoho_updateExistingCreditNote";
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

    /**
     * Positive test case for deleteExisistingCreditNotes method with mandatory parameters.
     */
    @Test(priority = 2, enabled = false, groups = {"wso2.esb"}, description = "zoho {deleteExisistingCreditNote} integration test")
    public void testDeleteExisistingCreditNoteWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "deleteExisistingCreditNote_mandatory.txt";
        String methodName = "zoho_deleteExisistingCreditNote";
        final String jsonString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String proxyFilePath = "file:///" + pathToProxiesDirectory + methodName + ".xml";
        String modifiedJsonString = String.format(jsonString,
                zohoConnectorProperties.getProperty("authToken"), zohoConnectorProperties.getProperty("creditNoteId")
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
     * Negative test case for deleteExisistingCreditNotes method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {deleteExisistingCreditNote} integration test")
    public void testDeleteExisistingCreditNoteWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "deleteExisistingCreditNote_negative.txt";
        String methodName = "zoho_deleteExisistingCreditNote";
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

    /**
     * Positive test case for getTheListOfCreditedInvoices method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getTheListOfCreditedInvoices} integration test")
    public void testGetTheListOfCreditedInvoicesWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfCreditedInvoices_mandatory.txt";
        String methodName = "zoho_getTheListOfCreditedInvoices";
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
     * Negative test case for getTheListOfCreditedInvoices method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getTheListOfCreditedInvoices} integration test")
    public void testGetTheListOfCreditedInvoicesWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfCreditedInvoices_negative.txt";
        String methodName = "zoho_getTheListOfCreditedInvoices";
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

    /**
     * Negative test case for applyCreditNoteToExistingInvoices method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {applyCreditNoteToExistingInvoices} integration test")
    public void testApplyCreditNoteToExistingInvoicesWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "applyCreditNoteToExistingInvoices_negative.txt";
        String methodName = "zoho_applyCreditNoteToExistingInvoices";
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
     * Positive test case for getTheListOfRefunds method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getTheListOfRefunds} integration test")
    public void testgetTheListOfRefundsWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfRefunds_mandatory.txt";
        String methodName = "zoho_getTheListOfRefunds";
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
     * Positive test case for getTheListOfRefunds method with optional parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getTheListOfRefunds} integration test")
    public void testgetTheListOfRefundsWithOptionalParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfRefunds_optional.txt";
        String methodName = "zoho_getTheListOfRefunds";
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
     * Negative test case for getTheListOfRefunds method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getTheListOfRefunds} integration test")
    public void testgetTheListOfRefundsWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getTheListOfRefunds_negative.txt";
        String methodName = "zoho_getTheListOfRefunds";
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
     * Positive test case for getDetailOfACreditNoteRefund method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getDetailOfACreditNoteRefund } integration test")
    public void testGetDetailOfACreditNoteRefundWithRequiredParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfACreditNoteRefund_mandatory.txt";
        String methodName = "zoho_getDetailOfACreditNoteRefund";

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
     * Negative test case for getDetailOfACreditNoteRefund method with mandatory parameters.
     */
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {getDetailOfACreditNoteRefund } integration test")
    public void testGetDetailOfACreditNoteRefundWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "getDetailOfACreditNoteRefund_negative.txt";
        String methodName = "zoho_getDetailOfACreditNoteRefund ";
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

    //negative parameters
    @Test(enabled = false, groups = {"wso2.esb"}, description = "zoho {createANewRefundCreditNote} integration test")
    public void testCreateANewRefundCreditNoteWithNegativeParameters() throws Exception {
        String jsonRequestFilePath = pathToRequestsDirectory + "createANewRefundCreditNote_negative.txt";
        String methodName = "zoho_createANewRefundCreditNote";
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
}
