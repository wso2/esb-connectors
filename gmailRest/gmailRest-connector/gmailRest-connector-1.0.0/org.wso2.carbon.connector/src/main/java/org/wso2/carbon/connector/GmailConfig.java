/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.connector;

import com.google.code.javax.mail.MessagingException;
import org.apache.axiom.om.OMText;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.Entry;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.registry.Registry;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.util.Properties;

/**
 * Class which reads OAuth access token and user name parameters from the
 * message context to perform OAuth authentication for Gmail.
 */
public class GmailConfig extends AbstractConnector {

    /*
     * Extracts the values for OAuth access token and user name from the message
     * context and stores them in the message context.
     */
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            // Reading OAuth access token and user name from the message context
            String oauthAccessToken = null;
            String username =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_PARAM_USERNAME);

            String oauthConsumerKey =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_OAUTH_CONSUMER_KEY);

            String oauthConsumerSecret =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_OAUTH_CONSUMER_SECRET);

            String oauthRefreshToken =
                    GmailUtils.lookupFunctionParam(messageContext,
                            GmailConstants.GMAIL_OAUTH_REFRESH_TOKEN);

            String registryTokenValue = GmailUtils.getRegistryResourceValue(messageContext, "gov:/AccessTokens/gmail");

            if (registryTokenValue == null) {
                GmailUtils.storeAccessToken("gov:/AccessTokens/gmail", oauthAccessToken, messageContext);
                oauthAccessToken = GmailUtils.lookupFunctionParam(
                        messageContext, GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN);
            }
            else {
                oauthAccessToken = registryTokenValue;
            }

            if (GmailUtils.validateToken(oauthAccessToken)) {
                log.info("Access Token Valid ...");
            }
            else {
                log.info("Invalid Access Token Found ...");
                oauthAccessToken = GmailUtils.getNewAccessToken(messageContext);
                log.info("Retrieved Access Token Successfully");
                GmailUtils.storeAccessToken("gov:/AccessTokens/gmail", oauthAccessToken, messageContext);
            }

            // Validating the user name and OAuth access token provided by the
            // user
            if (username == null || "".equals(username.trim()) || oauthAccessToken == null ||
                    "".equals(oauthAccessToken.trim())) {

                String errorLog = "Invalid username or access token";
                log.error(errorLog);
                ConnectException connectException = new ConnectException(errorLog);
                GmailUtils.storeErrorResponseStatus(messageContext,
                        connectException,
                        GmailErrorCodes.GMAIL_ERROR_CODE_CONNECT_EXCEPTION);
                handleException(connectException.getMessage(), connectException, messageContext);
            }

            // Storing OAuth user login details in the message context
            this.storeOauthUserLogin(messageContext, username, oauthAccessToken, oauthConsumerKey,
                    oauthConsumerSecret, oauthRefreshToken);
        } catch (MessagingException e) {
            GmailUtils.storeErrorResponseStatus(messageContext,
                    e,
                    GmailErrorCodes.GMAIL_ERROR_CODE_MESSAGING_EXCEPTION);
            handleException(e.getMessage(), e, messageContext);
        } catch (Exception e) {
            GmailUtils.storeErrorResponseStatus(messageContext, e,
                    GmailErrorCodes.GMAIL_COMMON_EXCEPTION);
            handleException(e.getMessage(), e, messageContext);
        }
    }

    /**
     * Stores user name and access token for OAuth authentication
     *
     * @param messageContext
     *            message context where the user login information should be
     *            stored
     * @param username
     *            user name
     * @param oauthAccessToken
     *            access token
     * @throws com.google.code.javax.mail.MessagingException
     */
    private void storeOauthUserLogin(MessageContext messageContext, String username,
                                     String oauthAccessToken, String oauthConsumerKey,
                                     String oauthConsumerSecret, String oauthRefreshToken) throws MessagingException {
        org.apache.axis2.context.MessageContext axis2MessageContext =
                ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        Object loginMode = axis2MessageContext.getProperty(GmailConstants.GMAIL_LOGIN_MODE);
        if (loginMode != null &&
                (loginMode.toString() == GmailConstants.GMAIL_OAUTH_LOGIN_MODE) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_USERNAME).toString()
                        .equals(username) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN).toString()
                        .equals(oauthAccessToken) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_CONSUMER_SECRET).toString()
                        .equals(oauthConsumerSecret) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_CONSUMER_KEY).toString()
                        .equals(oauthConsumerKey) &&
                messageContext.getProperty(GmailConstants.GMAIL_OAUTH_REFRESH_TOKEN).toString()
                        .equals(oauthRefreshToken)) {
            log.info("The same authentication is already available. Hence no changes are needed.");
            return;
        }

        // Reset already stored instances
        GmailUtils.closeConnection(axis2MessageContext);

        log.info("Setting the loggin mode to \"OAUTH\"");
        axis2MessageContext.setProperty(GmailConstants.GMAIL_LOGIN_MODE,
                GmailConstants.GMAIL_OAUTH_LOGIN_MODE);
        log.info("Storing new username and access token");
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_USERNAME, username);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_ACCESS_TOKEN, oauthAccessToken);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_CONSUMER_KEY, oauthConsumerKey);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_CONSUMER_SECRET, oauthConsumerSecret);
        messageContext.setProperty(GmailConstants.GMAIL_OAUTH_REFRESH_TOKEN, oauthRefreshToken);
    }
}
