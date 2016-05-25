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
package org.wso2.carbon.inbound.ibmmq.poll;

import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.constants.MQConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.SynapseEnvironment;
import org.wso2.carbon.inbound.endpoint.protocol.generic.GenericPollingConsumer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class IbmMqConsumer extends GenericPollingConsumer {

    private static final Log log = LogFactory.getLog(IbmMqConsumer.class);
    MQQueueManager queueManager;
    private MQQueue queue;
    private boolean isConnected;
    Hashtable<String, Object> mqProperties = new Hashtable<String, Object>();

    public IbmMqConsumer(Properties ibmMqProperties, String name, SynapseEnvironment synapseEnvironment,
                         long scanInterval, String injectingSeq, String onErrorSeq, boolean coordination,
                         boolean sequential) {
        super(ibmMqProperties, name, synapseEnvironment, scanInterval, injectingSeq, onErrorSeq, coordination,
                sequential);
        log.info("Initialized the IBM MQ consumer");
        setupConnection();
    }


    public Object poll() {
        if (!isConnected) {
            setupConnection();
        }
        if (isConnected) {
            messageFromQueue();
        }
        return null;
    }

    /**
     * Injecting the IBM MQ to the sequence
     *
     * @param message the IBM MQ response message
     */
    public void injectIbmMqMessage(String message) {
        if (injectingSeq != null) {
            String content = properties.getProperty(IbmMqConstants.CONTENT_TYPE);
            injectMessage(message, content);
            if (log.isDebugEnabled()) {
                log.debug("Injecting IBM MQ  message to the sequence : " + injectingSeq);
            }
        } else {
            handleException("The Sequence is not found");
        }
    }

    /**
     * Setting up the connection
     */
    private void setupConnection() {
        if (log.isDebugEnabled()) {
            log.debug("Starting to setup the connection with the IBM MQ");
        }
        String hostname = properties.getProperty(IbmMqConstants.HOST_NAME);
        String channel = properties.getProperty(IbmMqConstants.CHANNEL);
        String qName = properties.getProperty(IbmMqConstants.MQ_QUEUE);
        String qManager = properties.getProperty(IbmMqConstants.MQ_QMGR);
        String port = properties.getProperty(IbmMqConstants.PORT);
        String userName = properties.getProperty(IbmMqConstants.USER_ID);
        String password = properties.getProperty(IbmMqConstants.PASSWORD);
        String sslEnabledS = properties.getProperty(IbmMqConstants.SSL_ENABLED);
        boolean sslEnabled = Boolean.parseBoolean(sslEnabledS);
        mqProperties.put(MQConstants.HOST_NAME_PROPERTY, hostname);
        mqProperties.put(MQConstants.PORT_PROPERTY, new Integer(port));
        mqProperties.put(MQConstants.CHANNEL_PROPERTY, channel);
        mqProperties.put(MQConstants.USER_ID_PROPERTY, userName);
        mqProperties.put(MQConstants.PASSWORD_PROPERTY, password);
        if (sslEnabled) {
            sslConnection();
        }
        /**
         * Connect to a queue manager
         */
        try {
            queueManager = new MQQueueManager(qManager, mqProperties);
            int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF;
            queue = queueManager.accessQueue(qName, openOptions);
            log.info("IBM MQ " + qManager + " queue manager successfully connected");
            isConnected = true;
        } catch (MQException e) {
            handleException("Error while setup the IBM MQ connection", e);
            isConnected = false;
        }
    }

    /**
     * connection with SSL
     */
    public void sslConnection() {
        String keyStoreLocation = properties.getProperty(IbmMqConstants.SSL_KEYSTORE_LOCATION);
        String keyStoreType = properties.getProperty(IbmMqConstants.SSL_KEYSTORE_TYPE);
        String keyStorePassword = properties.getProperty(IbmMqConstants.SSL_KEYSTORE_PASSWORD);
        String trustStoreLocation = properties.getProperty(IbmMqConstants.SSL_TRUSTSTORE_LOCATION);
        String trustStoreType = properties.getProperty(IbmMqConstants.SSL_TRUSTSTORE_TYPE);
        String sslVersion = properties.getProperty(IbmMqConstants.SSL_VERSION);
        String sslFipsRequired = properties.getProperty(IbmMqConstants.SSL_FIPS);
        String sslCipherSuite = properties.getProperty(IbmMqConstants.SSL_CIPHERSUITE);
        boolean sslFips = Boolean.parseBoolean(sslFipsRequired);
        try {
            char[] keyPassphrase = keyStorePassword.toCharArray();
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            ks.load(new FileInputStream(keyStoreLocation), keyPassphrase);
            KeyStore trustStore = KeyStore.getInstance(trustStoreType);
            trustStore.load(new FileInputStream(trustStoreLocation), null);

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(trustStore);
            keyManagerFactory.init(ks, keyPassphrase);
            SSLContext sslContext = SSLContext.getInstance(sslVersion);
            sslContext.init(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(), null);
            mqProperties.put(MQConstants.SSL_SOCKET_FACTORY_PROPERTY, sslContext);
            mqProperties.put(MQConstants.SSL_FIPS_REQUIRED_PROPERTY, sslFips);
            mqProperties.put(MQConstants.SSL_CIPHER_SUITE_PROPERTY, sslCipherSuite);
        } catch (Exception ex) {
            handleException(ex.getMessage());
        }
    }

    /**
     * Receiving message from queue
     */
    private void messageFromQueue() {
        try {
            MQMessage rcvMessage = new MQMessage();
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            queue.get(rcvMessage, gmo);
            String msgText = rcvMessage.readUTF();
            injectIbmMqMessage(msgText);
        } catch (MQException e) {
            int reason = e.reasonCode;
            if (MQConstants.MQRC_CONNECTION_BROKEN == reason) {
                isConnected = false;
                log.error("IBM MQ Connection Broken");
            } else if (MQConstants.MQRC_NO_MSG_AVAILABLE != reason) {
                log.error("Error while getting messages from queue", e);
            }
        } catch (IOException e) {
            handleException("", e);
        }
    }

    /**
     * @param msg message to set for the exception
     * @param ex  throwable to set
     */
    public void handleException(String msg, Exception ex) {
        log.error(msg, ex);
        throw new SynapseException(ex);
    }

    /**
     * @param msg message to set for the exception
     */
    private void handleException(String msg) {
        log.error(msg);
        throw new SynapseException(msg);
    }
}
