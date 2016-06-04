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

package org.wso2.carbon.connector.integration.test.ejb2;

import com.ejb2connector.stateless.Hello;
import com.ejb2connector.stateless.HelloHome;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class CheckStateless {

    /**
     * @param context context.
     * @return sum.
     * @throws RemoteException
     * @throws CreateException
     * @throws NamingException
     */
    public static int getFromStateless(Context context) throws RemoteException, CreateException, NamingException {
        HelloHome home = (HelloHome) context.lookup("EJB2StatelessJboss");
        Hello bean = home.create();
        // invoke on the remote calculator
        int a = 10;
        int b = 12;
        return bean.add(a, b);
    }
}