/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.custom.connector;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;

import java.lang.reflect.Method;

/**
 * Work with stateless bean.
 *
 * @since 1.0.0
 */
public class CallEJBStatelessBean extends AbstractConnector {

    /**
     * Method to connect with stateless bean.
     *
     * @param messageContext the MessageContext.
     */
    @Override
    public void connect(MessageContext messageContext) {
        String methodName = getParameter(messageContext, EJBConstants.METHOD_NAME).toString();
        String returnName = (String) getParameter(messageContext, EJBConstants.RETURN);
        if (getParameter(messageContext, EJBConstants.RETURN) == null) {
            returnName = EJBConstants.RESPONSE;
        }
        Object ejbObj = EJBUtil.getEJBObject(messageContext, EJBConstants.JNDI_NAME);
        Object[] arguments = EJBUtil.buildArguments(messageContext, EJBConstants.STATELESS);
        Method method = EJBUtil.resolveMethod(ejbObj.getClass(), methodName, arguments.length);
        Object result = EJBUtil.invokeInstanceMethod(ejbObj, method, arguments);
        if (!method.getReturnType().toString().equals(EJBConstants.VOID)) {
            messageContext.setProperty(returnName, result);
        } else {
            messageContext.setProperty(EJBConstants.RESPONSE, EJBConstants.SUCCESS);
        }
    }
}