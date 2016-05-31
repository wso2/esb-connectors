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

package org.wso2.carbon.inbound.iso8583.listening;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;
import org.wso2.carbon.inbound.endpoint.protocol.generic.GenericInboundListener;

public class ISO8583MessageConsumer extends GenericInboundListener {
    private static final Log log = LogFactory.getLog(ISO8583MessageConsumer.class);
    private int port;
    private String injectingSeq;
    private InboundProcessorParams params;
    private String onErrorSeq;
    private boolean sequential;
    private SynapseEnvironment synapseEnvironment;
    private Properties properties;
    private ISO8583MessageConnection con;

    public ISO8583MessageConsumer(InboundProcessorParams params) throws ISOException, IOException {
        super(params);
        this.injectingSeq = params.getInjectingSeq();
        this.onErrorSeq = params.getOnErrorSeq();
        this.name = params.getName();
        this.synapseEnvironment = params.getSynapseEnvironment();
        this.properties = params.getProperties();
        this.params = params;
        this.port = Integer.parseInt(properties.getProperty(ISO8583Constant.PORT));
        this.sequential = Boolean.parseBoolean(properties.getProperty(ISO8583Constant.INBOUND_SEQUENTIAL));
        this.con = new ISO8583MessageConnection(port, params);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void init() {
        try {
            con.start();
        } catch (Exception e) {
            log.error("Could not start the thread", e);
        }
    }

    @Override
    public void destroy() {
        con.destroyConnection();
    }
}
