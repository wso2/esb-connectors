/**
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

package org.wso2.carbon.custom.connector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;

public class EjbException {
    protected Log log = LogFactory.getLog(this.getClass());
    protected static final Log trace = LogFactory.getLog(EJBConstants.TRACE_LOGGER);
    protected int traceState = 2;

    protected void handleException(String msg, Exception e, MessageContext messageContext) {
        this.log.error(msg, e);
        if (messageContext.getServiceLog() != null) {
            messageContext.getServiceLog().error(msg, e);
        }
        if (this.shouldTrace(messageContext.getTracingState())) {
            trace.error(msg, e);
        }
        throw new SynapseException(msg, e);
    }

    public boolean shouldTrace(int parentTraceState) {
        return this.traceState == 1 || this.traceState == 2 && parentTraceState == 1;
    }

    protected void handleException(String msg, MessageContext messageContext) {
        this.log.error(msg);
        if (messageContext.getServiceLog() != null) {
            messageContext.getServiceLog().error(msg);
        }
        if (this.shouldTrace(messageContext.getTracingState())) {
            trace.error(msg);
        }
        throw new SynapseException(msg);
    }
}
