/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.wso2.carbon.inbound.endpoint.protocol.generic.GenericInboundListener;

public class ISO8583MessageConsumer extends GenericInboundListener {
    private static final Log log = LogFactory.getLog(ISO8583MessageConsumer.class);
    private int port;
    private ISO8583MessageConnection con;
    public InboundProcessorParams params;

    public ISO8583MessageConsumer(InboundProcessorParams params)  {
        super(params);
        Properties properties = params.getProperties();
        this.params = params;
        try {
           this.port = Integer.parseInt(properties.getProperty(ISO8583Constant.PORT));
        } catch (NumberFormatException e) {
            handleException("The String does not contain a parsable integer", e);
        }
        this.con = new ISO8583MessageConnection(properties,port, params);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void init() {
       con.start();
    }

    @Override
    public void destroy() {
        con.destroyConnection();
    }
}



