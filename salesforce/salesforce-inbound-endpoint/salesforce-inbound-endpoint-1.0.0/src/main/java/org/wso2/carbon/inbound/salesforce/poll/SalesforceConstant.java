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

public class SalesforceConstant {

    private SalesforceConstant(){
    }

    //property values for the salesforce inbound endpoint
    public static final String USER_NAME = "connection.salesforce.userName";
    public static final String PASSWORD = "connection.salesforce.password";
    public static final String LOGIN_ENDPOINT = "connection.salesforce.loginEndpoint";
    public static final String PACKAGE_NAME = "connection.salesforce.packageName";
    public static final String PACKAGE_VERSION = "connection.salesforce.packageVersion";
    public static final String SOAP_API_VERSION = "connection.salesforce.soapApiVersion";

    //object for the salesforce inbound endpoint
    public static final String SOBJECT = "connection.salesforce.salesforceObject";

    //default parameters for the salesforce inbound endpoint
    public static final String CONNECTION_TIMEOUT = "connection.salesforce.connectionTimeout";
    public static final int CONNECTION_TIMEOUT_DEFAULT = 10 * 1000;
    public static final String READ_TIMEOUT = "connection.salesforce.readTimeout";
    public static final int READ_TIMEOUT_DEFAULT = 10 * 1000;
    public static final String WAIT_TIME = "connection.salesforce.waitTime";
    public static final int WAIT_TIME_DEFAULT = 24 * 60 * 60 * 1000;

    //content type of the message
    public static final String CONTENT_TYPE = "application/json";

    public static final String COOKIE_LANGUAGE_KEY = "language";
    public static final String COOKIE_LANGUAGE_DEFAULT_VALUE = "en_US";
    public static final String COOKIE_SESSION_ID_KEY = "sid";
    public static final String COOKIE_LOGIN_KEY = "login";
    public static final String COOKIE_LOCALEINFO_KEY = "com.salesforce.LocaleInfo";
    public static final String COOKIE_LOCALEINFO_DEFAULT_VALUE = "us";
    public static final String ERROR = "error";
    public static final String EXCEPTION = "exception";

    // The enterprise SOAP API endpoint used for the login call in this example.
    public static final String SERVICES_SOAP_PARTNER_ENDPOINT = "/services/Soap/u/" + SOAP_API_VERSION;

    public static final String ENV_START =
            "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
                    + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " +
                    "xmlns:urn='urn:partner.soap.sforce.com'><soapenv:Body>";

    public static final String ENV_END = "</soapenv:Body></soapenv:Envelope>";
}