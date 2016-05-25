package org.wso2.carbon.inbound.ibmmq.poll;
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
public class IbmMqConstants {

    //property  for the ibm mq inbound endpoint
    public static final String HOST_NAME = "ibm.hostname";
    public static final String CHANNEL = "ibm.channel";
    public static final String PORT = "ibm.port";
    public static final String MQ_QUEUE = "ibm.queue";
    public static final String MQ_QMGR = "ibm.qmanager";
    public static final String USER_ID = "ibm.userid";
    public static final String PASSWORD = "ibm.password";

    //SSL related properties
    public static final String SSL_ENABLED = "ibm.ssl.enabled";
    public static final String SSL_VERSION = "ibm.ssl.version";
    public static final String SSL_KEYSTORE_LOCATION = "ibm.ssl.keystore.location";
    public static final String SSL_KEYSTORE_TYPE = "ibm.ssl.keystore.type";
    public static final String SSL_KEYSTORE_PASSWORD = "ibm.ssl.keystore.password";

    public static final String SSL_TRUSTSTORE_LOCATION = "ibm.ssl.truststore.location";
    public static final String SSL_TRUSTSTORE_TYPE = "ibm.ssl.truststore.type";
    public static final String SSL_CIPHERSUITE = "ibm.ssl.ciphersuite";
    public static final String SSL_FIPS = "ibm.ssl.fips";


    //content type of the message
    public static final String CONTENT_TYPE = "content";
}

