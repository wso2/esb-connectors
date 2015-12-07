/**
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
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
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.lang.reflect.Method;

public class EJBStateful extends AbstractConnector {
    private static final Log log = LogFactory.getLog(EJBStateful.class);

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        EJBUtil ejbUtil = new EJBUtil();
        Object ejbObj;
        String methodName = (String) getParameter(messageContext, EJBConstants.METHOD_NAME);
        String returnName = (String) getParameter(messageContext, EJBConstants.RETURN);
        if (getParameter(messageContext, EJBConstants.RETURN) == null) {
            returnName = EJBConstants.RESPONSE;
        }
        if (messageContext.getProperty(messageContext.getMessageID()) == null) {
            ejbObj = ejbUtil.getEJBObject(messageContext, EJBConstants.JNDI_NAME);
            messageContext.setProperty(messageContext.getMessageID(), ejbObj);
        } else {
            ejbObj = messageContext.getProperty(messageContext.getMessageID());
        }
        Object[] args = ejbUtil.buildArguments(messageContext, EJBConstants.STATEFUL);
        Method method = ejbUtil.resolveMethod(ejbObj.getClass(), methodName, args.length, messageContext);
        Object obj = ejbUtil.invokeInstanceMethod(ejbObj, method, args, messageContext);
        if (!method.getReturnType().toString().equals(EJBConstants.VOID)) {
            messageContext.setProperty(returnName, obj);
        } else {
            messageContext.setProperty(EJBConstants.RESPONSE, EJBConstants.SUCCESS);
        }
    }
}