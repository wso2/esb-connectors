/*
Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.connector.twitter;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;
import org.wso2.carbon.connector.common.Base64Coder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TwitterConnectorTest extends ConnectorIntegrationTestBase {
	private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

	private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

	/**
	 * Set up the environment.
	 */
	@BeforeClass(alwaysRun = true)
	public void setEnvironment() throws Exception {

		init("twitterRest-connector-1.0.0");

		esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
		esbRequestHeadersMap.put("Content-Type", "application/json");

		apiRequestHeadersMap.putAll(esbRequestHeadersMap);

		String authHeader = connectorProperties.getProperty("siteName") + "\\" + connectorProperties.getProperty("username") + ":" + connectorProperties.getProperty("password");
		String encodedAuthorization = Base64Coder.encodeString(authHeader);

		apiRequestHeadersMap.put("Authorization", "Basic " + encodedAuthorization);
		apiRequestHeadersMap.put("Content-Type", "application/json");
		apiRequestHeadersMap.put("Accept", "application/json");
		;
	}

	/**
	 * Positive test case for getContactFields method with mandatory parameters.
	 */
	@Test(enabled = true, description = "twitter {getContactFields} integration test with mandatory parameters.")
	public void testGetContactFieldsWithMandatoryParameters() throws IOException, JSONException {

		String methodName = "getContactFields";
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "contacts/fields";
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "getContactFields.json");
		RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

		Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
	}

	/**
	 * Positive test case for getContactFields method with optional parameters.
	 */
	@Test(enabled = true, dependsOnMethods = {"testGetContactFieldsWithMandatoryParameters"}, description = "twitter {getContactFields} integration test with optional parameters.")
	public void testGetContactFieldsWithOptionalParameters() throws IOException, JSONException {

		String methodName = "getContactFields";
		String apiEndPoint =
				connectorProperties.getProperty("apiUrl") + "contacts/fields?totalResults=" + connectorProperties.getProperty("totalResults") + "&q=" + connectorProperties.getProperty("q") +
						"&orderBy=" + connectorProperties.getProperty("orderByEncoded") + "&offset=" + connectorProperties.getProperty("offset") + "&limit=" + connectorProperties.getProperty("limit");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "getContactFieldsOptional.json");

		RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
		System.out.println(apiRestResponse.getBody().toString());
		Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
	}

	/**
	 * Positive test case for getContactFieldById method with mandatory parameters.
	 */
	@Test(enabled = true, dependsOnMethods = {"testGetContactFieldsWithOptionalParameters"}, description = "twitter {getContactFieldById} integration test with mandatory parameters.")
	public void testGetContactFieldByIdWithMandatoryParameters() throws IOException, JSONException {
		String methodName = "getContactFieldById";
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "contacts/fields/" + connectorProperties.getProperty("contactFieldId");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(getProxyServiceURL(methodName), "POST", esbRequestHeadersMap, "getContactFieldById.json");
		RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
		Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
	}

}