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

import java.util.Set;

public class ZrevrangeByScore extends AbstractMediator {

    public boolean mediate(MessageContext messageContext) {
        try {
            Jedis jedis;
            RedisServer serverobj = new RedisServer();
            jedis = serverobj.connect(messageContext);
            if (jedis != null) {
                String key = messageContext.getProperty(RedisConstants.KEY).toString();
                Double min = Double.parseDouble(messageContext.getProperty(RedisConstants.MIN).toString());
                Double max = Double.parseDouble(messageContext.getProperty(RedisConstants.MAX).toString());
                Set<String> response = jedis.zrevrangeByScore(key, min, max);
                if (response != null) {
                    messageContext.setProperty(RedisConstants.RESULT, response.toString());
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return true;
    }
}
