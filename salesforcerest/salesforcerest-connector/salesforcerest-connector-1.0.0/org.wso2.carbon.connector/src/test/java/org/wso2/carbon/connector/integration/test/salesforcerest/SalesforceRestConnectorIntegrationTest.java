package org.wso2.carbon.connector.integration.test.salesforcerest;

/*
 *
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 * /
 */
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SalesforceRestConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> mpRequestHeadersMap = new HashMap<String, String>();

    private String multipartProxyUrl;

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        init("salesforcerest-connector-1.0.0");
        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");

        apiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        apiRequestHeadersMap.put("Content-Type", "application/json");

        String accessToken = connectorProperties.getProperty("accessToken");
        apiRequestHeadersMap.put("Authorization", "Bearer " + accessToken);
    }

    /**
     * Test case for describeSGlobal method.
     */
    @Test(enabled = true, description = "salesforcerest {describeGlobal} integration test.")
    public void describeGlobal() throws IOException, JSONException {

        String methodName = "describeGlobal";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "describeGlobal.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("sobjects"), apiRestResponse.getBody().getString("sobjects"));
    }

    /**
     * Test case for describeSObject method.
     */
    @Test(enabled = true, description = "salesforcerest {describeSObject} integration test.")
    public void describeSObject() throws IOException, JSONException {

        String methodName = "describeSObject";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "describeSObject.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject") + "/describe";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getJSONArray("childRelationships").getJSONObject(1).getString("childSObject"), apiRestResponse.getBody().getJSONArray("childRelationships").getJSONObject(1).getString("childSObject"));
    }

    /**
     * Test case for sObjectBasicInfo method.
     */
    @Test(enabled = true, description = "salesforcerest {sObjectBasicInfo} integration test.")
    public void sObjectBasicInfo() throws IOException, JSONException {

        String methodName = "sObjectBasicInfo";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "sObjectBasicInfo.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("objectDescribe"), apiRestResponse.getBody().getString("objectDescribe"));
    }

    /**
     * Test case for sObjectGetDeleted method.
     */
    @Test(enabled = true, description = "salesforcerest {sObjectGetDeleted} integration test.")
    public void sObjectGetDeleted() throws IOException, JSONException {

        String methodName = "sObjectGetDeleted";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "sObjectGetDeleted.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject") + "/deleted/?start=" + connectorProperties.getProperty("apiStartTime") + "&end=" + connectorProperties.getProperty("apiEndTime");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("deletedRecords"), apiRestResponse.getBody().getString("deletedRecords"));
    }

    /**
     * Test case for sObjectGetUpdated method.
     */
    @Test(enabled = true, description = "salesforcerest {sObjectGetUpdated} integration test.")
    public void sObjectGetUpdated() throws IOException, JSONException {

        String methodName = "sObjectGetUpdated";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "sObjectGetUpdated.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject") + "/updated/?start=" + connectorProperties.getProperty("apiStartTime") + "&end=" + connectorProperties.getProperty("apiEndTime");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("ids"), apiRestResponse.getBody().getString("ids"));
    }

    /**
     * Test case for sObjectRows method.
     */
    @Test(enabled = true, description = "salesforcerest {sObjectRows} integration test.")
    public void sObjectRows() throws IOException, JSONException {

        String methodName = "sObjectRows";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "sObjectRows.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject") + "/" + connectorProperties.getProperty("rowId");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("LastModifiedDate"), apiRestResponse.getBody().getString("LastModifiedDate"));
    }


    /**
     * Test case for listResourcesByApiVersion method.
     */
    @Test(enabled = true, description = "salesforcerest {listResourcesByApiVersion} integration test.")
    public void listResourcesByApiVersion() throws IOException, JSONException {

        String methodName = "listResourcesByApiVersion";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "listResourcesByApiVersion.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("sobjects"), apiRestResponse.getBody().getString("sobjects"));
    }

    /**
     * Test case for query method.
     */
    @Test(enabled = true, description = "salesforcerest {query} integration test.")
    public void query() throws IOException, JSONException {

        String methodName = "query";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "query.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/query/?q=" + connectorProperties.getProperty("queryString");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("records"), apiRestResponse.getBody().getString("records"));
    }

    /**
     * Test case for queryPerformanceFeedback method.
     */
    @Test(enabled = true, description = "salesforcerest {queryPerformanceFeedback} integration test.")
    public void queryPerformanceFeedback() throws IOException, JSONException {

        String methodName = "queryPerformanceFeedback";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "queryPerformanceFeedback.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/query/?explain=" + connectorProperties.getProperty("queryString");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("plans"), apiRestResponse.getBody().getString("plans"));
    }

    /**
     * Test case for listviewQueryPerformanceFeedback method.
     */
    @Test(enabled = true, description = "salesforcerest {listviewQueryPerformanceFeedback} integration test.")
    public void listviewQueryPerformanceFeedback() throws IOException, JSONException {

        String methodName = "listviewQueryPerformanceFeedback";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "listviewQueryPerformanceFeedback.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/query/?explain=" + connectorProperties.getProperty("listViewID");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("plans"), apiRestResponse.getBody().getString("plans"));
    }


    /**
     * Test case for queryAll method.
     */
    @Test(enabled = true, description = "salesforcerest {queryAll} integration test.")
    public void queryAll() throws IOException, JSONException {

        String methodName = "queryAll";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "queryAll.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/queryAll/?q=" + connectorProperties.getProperty("queryString");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("records"), apiRestResponse.getBody().getString("records"));
    }
//
//    /**
//     * Test case for queryAllMore method.
//     */
//    @Test(enabled = true, description = "salesforcerest {queryAllMore} integration test.")
//    public void queryAllMore() throws IOException, JSONException {
//
//        String methodName = "queryAllMore";
//        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "queryAllMore.json");
//        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/queryAll/"+connectorProperties.getProperty("nextRecordsUrl");
//        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
//        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
//    }
//
//
//    /**
//     * Test case for queryMore method.
//     */
//    @Test(enabled = true, description = "salesforcerest {queryMore} integration test.")
//    public void queryMore() throws IOException, JSONException {
//
//        String methodName = "queryMore";
//        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "queryMore.json");
//        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/query/"+connectorProperties.getProperty("nextRecordsUrl");
//        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
//        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
//        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
//    }

    /**
     * Test case for getSpecificAction method.
     */
    @Test(enabled = true, description = "salesforcerest {getSpecificAction} integration test.")
    public void getSpecificAction() throws IOException, JSONException {

        String methodName = "getSpecificAction";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "getSpecificAction.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/quickActions/" + connectorProperties.getProperty("actionName");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("layout"), apiRestResponse.getBody().getString("layout"));
    }


    /**
     * Test case for quickActions method.
     */
    @Test(enabled = true, description = "salesforcerest {quickActions} integration test.")
    public void quickActions() throws IOException, JSONException {

        String methodName = "quickActions";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "quickActions.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/quickActions";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("output"), apiRestResponse.getBody().getString("output"));
    }


    /**
     * Test case for sObjectAction method.
     */
    @Test(enabled = true, description = "salesforcerest {sObjectAction} integration test.")
    public void sObjectAction() throws IOException, JSONException {

        String methodName = "sObjectAction";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "sObjectAction.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobject")+"/quickActions";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("output"), apiRestResponse.getBody().getString("output"));
    }

    /**
     * Test case for recentlyViewedItem method.
     */
    @Test(enabled = true, description = "salesforcerest {recentlyViewedItem} integration test.")
    public void recentlyViewedItem() throws IOException, JSONException {

        String methodName = "recentlyViewedItem";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "recentlyViewedItem.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/recent/?limit=" + connectorProperties.getProperty("limit");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
    }

    /**
     * Test case for retrieveFieldValues method.
     */
    @Test(enabled = true, description = "salesforcerest {retrieveFieldValues} integration test.")
    public void retrieveFieldValues() throws IOException, JSONException {

        String methodName = "retrieveFieldValues";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "retrieveFieldValues.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/sobjects/" + connectorProperties.getProperty("sobject") +"/"+connectorProperties.getProperty("rowId") + "?fields=" + connectorProperties.getProperty("fields");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
    }


    /**
     * Test case for search method.
     */
    @Test(enabled = true, description = "salesforcerest {search} integration test.")
    public void search() throws IOException, JSONException {

        String methodName = "search";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "search.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/search/?q=" + connectorProperties.getProperty("searchString");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
    }

    /**
     * Test case for searchResultLayout method.
     */
    @Test(enabled = true, description = "salesforcerest {searchResultLayout} integration test.")
    public void searchResultLayout() throws IOException, JSONException {

        String methodName = "searchResultLayout";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "searchResultLayout.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/" + connectorProperties.getProperty("apiVersion") + "/search/layout/?q=" + connectorProperties.getProperty("sObjectList");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());

    }

    /**
     * Test case for searchScopeAndOrder method.
     */
    @Test(enabled = true, description = "salesforcerest {searchScopeAndOrder} integration test.")
    public void searchScopeAndOrder() throws IOException, JSONException {

        String methodName = "searchScopeAndOrder";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "searchScopeAndOrder.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/search/scopeOrder";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("output"), apiRestResponse.getBody().getString("output"));
    }

    /**
     * Test case for getUserInformation method.
     */
    @Test(enabled = true, description = "salesforcerest {getUserInformation} integration test.")
    public void getUserInformation() throws IOException, JSONException {

        String methodName = "getUserInformation";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "getUserInformation.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/User/"+connectorProperties.getProperty("userId");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
    }

    /**
     * Test case for listViews method.
     */
    @Test(enabled = true, description = "salesforcerest {listViews} integration test.")
    public void listViews() throws IOException, JSONException {

        String methodName = "listViews";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "listViews.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobjectType")+"/listviews";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("listviews"), apiRestResponse.getBody().getString("listviews"));
    }

    /**
     * Test case for listViewById method.
     */
    @Test(enabled = true, description = "salesforcerest {listViewById} integration test.")
    public void listViewById() throws IOException, JSONException {

        String methodName = "listViewById";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "listViewById.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobjectType")+"/listviews/"+connectorProperties.getProperty("listViewID");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("developerName"), apiRestResponse.getBody().getString("developerName"));
    }

    /**
     * Test case for recentListViews method.
     */
    @Test(enabled = true, description = "salesforcerest {recentListViews} integration test.")
    public void recentListViews() throws IOException, JSONException {

        String methodName = "recentListViews";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "recentListViews.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobjectType")+"/listviews/recent";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("listviews"), apiRestResponse.getBody().getString("listviews"));
    }

    /**
     * Test case for listApprovals method.
     */
    @Test(enabled = true, description = "salesforcerest {listApprovals} integration test.")
    public void listApprovals() throws IOException, JSONException {

        String methodName = "listApprovals";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "listApprovals.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/process/approvals";
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("approvals"), apiRestResponse.getBody().getString("approvals"));
    }

    /**
     * Test case for create method.
     */
    @Test(enabled = true, description = "salesforcerest {create} integration test.")
    public void create() throws IOException, JSONException {

        String methodName = "create";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "create.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobject");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getString("id"), apiRestResponse.getBody().getJSONArray("recentItems").getJSONObject(0).getString("Id"));
    }

    /**
     * Test case for update method.
     */
    @Test(enabled = true, description = "salesforcerest {update} integration test.")
    public void update() throws IOException, JSONException {

        String methodName = "update";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "update.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobject")+"/"+connectorProperties.getProperty("Id");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap,"updateApi.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 204);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
    }

    /**
     * Test case for delete method.
     */
    @Test(enabled = true, description = "salesforcerest {delete} integration test.")
    public void delete() throws IOException, JSONException {

        String methodName = "delete";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "delete.json");
        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/"+connectorProperties.getProperty("sobject")+"/"+connectorProperties.getProperty("idToDelete");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 204);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 404);
    }

//    /**
//     * Test case for resetPassword method.
//     */
//    @Test(enabled = true, description = "salesforcerest {resetPassword} integration test.")
//    public void resetPassword() throws IOException, JSONException {
//
//        String methodName = "resetPassword";
//        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "resetPassword.json");
//        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/services/data/"+ connectorProperties.getProperty("apiVersion")+"/sobjects/User/"+connectorProperties.getProperty("userId")+"/password";
//        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
//        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
//        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
//    }

}
