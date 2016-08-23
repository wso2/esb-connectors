/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.inbound.salesforce.poll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Login with Salesforce.
 *
 */
public final class SoapLoginUtil {
    private static final Log log = LogFactory.getLog(SoapLoginUtil.class);

    private static String sessionId, loginUrl;

    /**
     * Build soap body to login with salesforce.
     * @param username the username for salesforce.
     * @param password the password for salesforce.
     * @throws UnsupportedEncodingException
     */
    private static byte[] soapXmlForLogin(String username, String password)
            throws UnsupportedEncodingException {
        return (SalesforceConstant.ENV_START +
                "  <urn:login>" +
                "    <urn:username>" + username + "</urn:username>" +
                "    <urn:password>" + password + "</urn:password>" +
                "  </urn:login>" +
                SalesforceConstant.ENV_END).getBytes("UTF-8");
    }

    /**
     * Login call with Salesforce.
     *
     * @param client    the HttpClient.
     * @param username  the username for Salesforce.
     * @param password  the password for salesforce
     * @throws IOException
     * @throws InterruptedException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static void login(HttpClient client, String username, String password) throws IOException, InterruptedException, SAXException,
            ParserConfigurationException {
        try {
            ContentExchange exchange = new ContentExchange();
            exchange.setMethod("POST");
            exchange.setURL(getSoapURL());
            exchange.setRequestContentSource(new ByteArrayInputStream(soapXmlForLogin(
                    username, password)));
            exchange.setRequestHeader("Content-Type", "text/xml");
            exchange.setRequestHeader("SOAPAction", "''");
            exchange.setRequestHeader("PrettyPrint", "Yes");

            client.send(exchange);
            exchange.waitForDone();

            String response = exchange.getResponseContent();
            String tagSession = "<sessionId>";
            String tagServerUrl = "<serverUrl>";
            String serverUrl = response.substring(response.indexOf(tagServerUrl) + tagServerUrl.length(), response.indexOf("</serverUrl>"));
            sessionId = response.substring(response.indexOf(tagSession) + tagSession.length(), response.indexOf("</sessionId>"));
            loginUrl = serverUrl.substring(0, serverUrl.indexOf("/services"));
        } catch (IndexOutOfBoundsException e) {
            throw new SynapseException("Login credentials of Salesforce is wrong....", e);
        } catch (MalformedURLException e) {
            SalesforceStreamData.handleException("Error while building URL", e);
        } catch (InterruptedException e) {
            SalesforceStreamData.handleException("Error in exchange the asynchronous message", e);
        } catch (UnsupportedEncodingException e) {
            SalesforceStreamData.handleException("Error in Encoding", e);
        } catch (IOException e) {
            SalesforceStreamData.handleException("Error while login to Salesforce", e);
        }
    }

    /**
     * Get Salesforce login Endpoint.
     */
    private static String getSoapURL() throws MalformedURLException {
        return new URL(SalesforceStreamData.loginEndpoint + getSoapUri()).toExternalForm();
    }

    /**
     * Get the enterprise SOAP API endpoint used for the login call.
     */
    private static String getSoapUri() {
        return SalesforceConstant.SERVICES_SOAP_PARTNER_ENDPOINT;
    }

    /**
     * Get sessionID to access Salesforce API.
     */
    public static String getSessionId() {
        return sessionId;
    }

    /**
     * Get Salesforce login URL endpoint.
     */
    public static String getEndpoint() {
        return loginUrl;
    }
}