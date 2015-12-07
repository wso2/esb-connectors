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

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

public class EJB2Init extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        Properties prop = new Properties();
        try {
            EJBUtil ejbUtil=new EJBUtil();
            Hashtable<String, String> dyValues = ejbUtil.getParameters(messageContext, EJBConstant.INIT);
            Set<String> set = dyValues.keySet();
            for (String aSet : set) {
                prop.setProperty(aSet, dyValues.get(aSet));
            }
            messageContext.setProperty((String)getParameter(messageContext, EJBConstant.KEY), prop);
        } catch (Exception e) {
            handleException("error while set the properties ", e, messageContext);
        }
    }
}