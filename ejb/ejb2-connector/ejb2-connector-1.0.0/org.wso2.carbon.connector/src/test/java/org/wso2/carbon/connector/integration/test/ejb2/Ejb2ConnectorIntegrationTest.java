/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.wso2.carbon.connector.integration.test.ejb2;

import junit.framework.Assert;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Ejb2ConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    Properties properties = new Properties();
    InitialContext context;
    private Map<String, String> esbRequestHeadersMap = new HashMap<>();

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        init("ejb2-connector-1.0.0");
        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");
        esbRequestHeadersMap.put("Accept", "application/json");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        properties.put(Context.PROVIDER_URL, "localhost");
        context = new InitialContext(properties);
    }

    /**
     * @throws Exception.
     */
    @Test(enabled = true, description = "Stateless Bean Jboss")
    public void statelessBean() throws Exception {
        String methodName = "ejb2Stateless";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName)
                , "GET", esbRequestHeadersMap, "stateless.json");
        Assert.assertEquals(esbRestResponse.getBody().get("Result"), checkStateless.getFromStateless(context));
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
    }

    /**
     * @throws Exception.
     */
    @Test(enabled = true, description = "Stateful Bean Jboss")
    public void statefulBean() throws Exception {
        String methodName = "ejb2Stateful";
        RestResponse<JSONObject> esbRestResponse = sendJsonRestRequest(getProxyServiceURL(methodName)
                , "GET", esbRequestHeadersMap, "stateful.json");
        Assert.assertEquals(esbRestResponse.getBody().get("Result"), checkStateful.getFromStateful(context));
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
    }
}