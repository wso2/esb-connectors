package org.wso2.carbon.connector.integration.test.fhir;

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

public class fhirConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> mpRequestHeadersMap = new HashMap<String, String>();

    private String multipartProxyUrl;

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        init("fhir-connector-1.0.0");
        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");

        apiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        apiRequestHeadersMap.put("Content-Type", "application/json");
    }

    /**
     * Test case for readResource method.
     */
    @Test(enabled = true, description = "fhir {readResource} integration test.")
    public void readResource() throws IOException, JSONException {

        String methodName = "readResource";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "readResource.json");
        String apiEndPoint = connectorProperties.getProperty("base") + "/" + connectorProperties.getProperty("type") + "?_format=" + connectorProperties.getProperty("format");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        System.out.println("ESBESBESBESBESBESBESBESBESBESB" + esbRestResponse.getBody().toString());
        System.out.println("APIAPIAPIAPIAPIAPIAPIAPIAPIAPIAPI" + apiRestResponse.getBody().toString());
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
//        Assert.assertEquals(esbRestResponse.getBody().getString(""), apiRestResponse.getBody().getString(""));
    }
}
