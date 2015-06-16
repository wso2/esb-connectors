/*
*  Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.connector.googlespreadsheet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.axiom.om.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.config.Entry;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.registry.Registry;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

import javax.net.ssl.HttpsURLConnection;

public class GoogleSpreadsheetUtils {

    private static Log log = LogFactory
            .getLog(GoogleSpreadsheetUtils.class);
	 public static String lookupFunctionParam(MessageContext ctxt, String paramName) {
	        return (String)ConnectorUtils.lookupTemplateParamater(ctxt, paramName);
	    }

	    public static void storeLoginUser(MessageContext ctxt, String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_CONSUMER_KEY, consumerKey);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_CONSUMER_SECRET, consumerSecret);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_ACCESS_TOKEN, accessToken);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_ACCESS_TOKEN_SECRET, accessTokenSecret);
	    }
	    
	    public static void storeLoginUserOAuth2(MessageContext ctxt, String consumerKey, String consumerSecret, String accessToken, String refreshToken) {
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_CONSUMER_KEY, consumerKey);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_CONSUMER_SECRET, consumerSecret);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_ACCESS_TOKEN, accessToken);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_REFRESH_TOKEN, refreshToken);
		}
	    
	    public static void storeLoginUsernamePassword(MessageContext ctxt, String userName, String password){
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_USERNAME, userName);
	        ctxt.setProperty(GoogleSpreadsheetConstants.GOOGLE_SPREADSHEET_USER_PASSWORD, password);
	    }

	    public static void storeErrorResponseStatus(MessageContext ctxt, Exception e) {
	        ctxt.setProperty(SynapseConstants.ERROR_EXCEPTION, e);
	        ctxt.setProperty(SynapseConstants.ERROR_MESSAGE, e.getMessage());

            if(ctxt.getEnvelope().getBody().getFirstElement() != null) {
                ctxt.getEnvelope().getBody().getFirstElement().detach();
            }

            OMFactory factory   = OMAbstractFactory.getOMFactory();
            OMNamespace ns      = factory.createOMNamespace("http://org.wso2.esbconnectors.googlespreadsheet", "ns");
            OMElement searchResult  = factory.createOMElement("ErrorResponse", ns);
            OMElement errorMessage      = factory.createOMElement("ErrorMessage", ns);
            searchResult.addChild(errorMessage);
            errorMessage.setText(e.getMessage());
            ctxt.getEnvelope().getBody().addChild(searchResult);
	    }


    public static void removeTransportHeaders(MessageContext synCtx) {
        // Removing transport headers
        Axis2MessageContext axis2smc = (Axis2MessageContext) synCtx;
        org.apache.axis2.context.MessageContext axis2MessageCtx =
                axis2smc.getAxis2MessageContext();
        Object headers = axis2MessageCtx.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map<String,String> headersMap = (Map) headers;
            headersMap.clear();
        } else {
            log.debug("No transport headers found for the message");
        }

    }

    public static String getNewAccessToken (MessageContext messageContext) {
        String validationUrl = "https://www.googleapis.com/oauth2/v3/token";
        try {
            URL urlObj = new URL(validationUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlObj.openConnection();

            httpsURLConnection.setRequestMethod("POST");
            String refreshToken = lookupFunctionParam(messageContext, "oauthRefreshToken");
            String clientId = lookupFunctionParam(messageContext, "oauthConsumerKey");
            String clientSecret = lookupFunctionParam(messageContext, "oauthConsumerSecret");
            String grantType = "refresh_token";

            String urlParameters = "refresh_token=" + refreshToken + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grantType;

            httpsURLConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = httpsURLConnection.getResponseCode();
            String inputLine;
            StringBuffer input = new StringBuffer("");

            if (responseCode == 200) {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                while ((inputLine = inputReader.readLine()) != null) {
                    input.append(inputLine);
                }

                Gson jsonResponse = new Gson();
                JsonObject jsonObject = jsonResponse.fromJson(input.toString(), JsonObject.class);

                return jsonObject.get("access_token").getAsString();
            }
            else {
                log.error("Could not Retrieve the Access token Via Refresh Token");
                return "";
            }

        } catch (MalformedURLException e) {
            System.out.println("Failed to validate access Token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static boolean validateToken (String accessToken) {
        String validationUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + accessToken;
        try {
            URL urlObj = new URL(validationUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlObj.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            if (httpsURLConnection.getResponseCode() == 400){
                return false;
            }
            else {
                return true;
            }

        } catch (MalformedURLException e) {
            System.out.println("Failed to validate access Token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getRegistryResourceValue (MessageContext msgCtx, String location, String username) {
        Registry registry = msgCtx.getConfiguration().getRegistry();
        String registryTokenValue;

        if (registry.getResourceProperties(location) == null) {
            registryTokenValue = null;
        }
        else {
            registryTokenValue = registry.getResourceProperties(location).getProperty(username);
        }

        return registryTokenValue;
    }

    public static void storeAccessToken (String location, String tokenValue, MessageContext msgCtx, String username) {
        Registry registry = msgCtx.getConfiguration().getRegistry();
        registry.newNonEmptyResource(location, false, "text/plain", tokenValue, username);
    }

}
