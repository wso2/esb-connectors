/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
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

import org.apache.synapse.Mediator;
import org.apache.synapse.MessageContext;

public class urlEncorder implements Mediator {
    public boolean mediate(MessageContext messageContext) {
        System.out.println(messageContext.getProperty("id"));
        System.out.println(messageContext.getProperty("label"));

        return false;
    }

    public String getType() {
        return null;
    }

    public int getTraceState() {
        return 0;
    }

    public void setTraceState(int i) {

    }

    public boolean isContentAware() {
        return false;
    }

    public void setDescription(String s) {

    }

    public String getDescription() {
        return null;
    }
}
