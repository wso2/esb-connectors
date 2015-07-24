package org.wso2.carbon.connector;

import org.apache.synapse.MessageContext;
import redis.clients.jedis.Jedis;

public class RedisServer {

    public Jedis connect(MessageContext messageContext){
        int port = 0;
        int timeout = 0;
        Jedis jedis = null;
        try {
            String host = messageContext.getProperty(RedisConstants.HOST).toString();
            if (messageContext.getProperty(RedisConstants.PORT) != "") {
                port = Integer.parseInt(messageContext.getProperty(RedisConstants.PORT).toString());
            }
            if (messageContext.getProperty(RedisConstants.TIMEOUT) != "") {
                timeout = Integer.parseInt(messageContext.getProperty(RedisConstants.TIMEOUT).toString());
            }
            if (port > 0 && timeout > 0) {
                jedis = new Jedis(host, port, timeout);
                return jedis;
            }else if (port > 0) {
                jedis = new Jedis(host, port);
                return jedis;
            } else {
                jedis = new Jedis(host);
                return jedis;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedis;
        
    }
}
