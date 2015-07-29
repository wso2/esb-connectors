/**
 Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 WSO2 Inc. licenses this file to you under the Apache License,
 Version 2.0 (the "License"); you may not use this file except
 in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
package org.wso2.carbon.connector;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import redis.clients.jedis.Jedis;

public class Sinterstore extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            Jedis jedis;
            RedisServer serverobj = new RedisServer();
            jedis = serverobj.connect(messageContext);
            if (jedis != null) {
                String key = messageContext.getProperty(RedisConstants.KEY).toString();
                System.out.println(key);
                String dstkey = messageContext.getProperty(RedisConstants.DSTKEY).toString();
                System.out.println(dstkey);
                Long response = null;
                String[] keyvalue = key.split(" ");
                ABC:
                for (int i = 0; i <= keyvalue.length; i++) {
                    if (i == keyvalue.length) {
                        break ABC;
                    }
                    System.out.println(keyvalue[i]);
                    response = jedis.sinterstore(dstkey, keyvalue[i]);
                    System.out.println(response.toString());
                }
                if (response != null) {
                    messageContext.setProperty(RedisConstants.RESULT, response);
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new SynapseException("Error while connecting the server or calling the redis method",e);
        }
    }
}
