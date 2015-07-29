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
import org.apache.synapse.mediators.AbstractMediator;
import redis.clients.jedis.Jedis;

public class Sadd extends AbstractMediator {

    public boolean mediate(MessageContext messageContext) {
        try {
            Jedis jedis;
            RedisServer serverobj = new RedisServer();
            jedis = serverobj.connect(messageContext);
            if (jedis != null) {
                String key = messageContext.getProperty(RedisConstants.KEY).toString();
                String members = messageContext.getProperty(RedisConstants.MEMBERS).toString();
                Long response = null;
                String[] keyvalue = members.split(" ");
                ABC:
                for (int i = 0; i <= keyvalue.length; i++) {
                    response = jedis.sadd(key, keyvalue[i], keyvalue[i + 1], keyvalue[i + 2], keyvalue[i + 3], keyvalue[i + 4]);
                    break;
                }
                if (response != null) {
                    messageContext.setProperty(RedisConstants.RESULT, response);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return true;
    }
}
