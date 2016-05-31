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

package org.wso2.carbon.inbound.feedep;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;


public class FeedEP extends GenericPollingConsumer {
    private static final Log log = LogFactory.getLog(FeedEP.class.getName());
    private String injectingSeq;
    private String onErrorSeq;
    private boolean sequential;
    private String host;
    private String feedType;
    private ConsumeFeed consume;
    private final FeedRegistryHandler registryHandler;
    private String dateFormat;
    private long scanInterval;

    public FeedEP(Properties properties, String name,
                  SynapseEnvironment synapseEnvironment, long scanInterval,
                  String injectingSeq, String onErrorSeq, boolean coordination,
                  boolean sequential) {
        super(properties, name, synapseEnvironment, scanInterval,
                injectingSeq, onErrorSeq, coordination, sequential);
        registryHandler = new FeedRegistryHandler();
        this.name = name;
        log.info("Scan Interval Passing " + scanInterval);
        this.scanInterval = scanInterval;
        this.sequential = true;
        this.host = properties.getProperty(FeedEPConstant.FEED_URL);
        this.feedType = properties.getProperty(FeedEPConstant.FEED_TYPE);
        log.info("URL : " + host + "Feed Type : " + feedType);
        if (properties.getProperty(FeedEPConstant.FEED_TIMEFORMAT) != null) {
            this.dateFormat = properties.getProperty(FeedEPConstant.FEED_TIMEFORMAT);
        }
        this.sequential = sequential;
        this.coordination = true;
        this.coordination = coordination;
        this.injectingSeq = injectingSeq;
        this.onErrorSeq = onErrorSeq;
        this.synapseEnvironment = synapseEnvironment;
        init1();
    }

//    public void destroy() {
//        try {
//            if (registryHandler.readFromRegistry(name) != null) {
//                registryHandler.deleteFromRegistry(name);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        super.destroy();
//    }

    public Object poll() {
        consume.execute();
        return null;
    }

    private void init1() {
        RssInject rssInject = new RssInject(injectingSeq, onErrorSeq, sequential,
                synapseEnvironment, FeedEPConstant.FEED_FORMAT);
        consume = new ConsumeFeed(rssInject, scanInterval, host, feedType, registryHandler, name, dateFormat);
    }
}