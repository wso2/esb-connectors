package org.wso2.carbon.connector.integration.test.redis;
/*
*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
* /
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedisConnectorIntegrationTest extends ConnectorIntegrationTestBase {
    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        init("redis-connector-1.0.0");

        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");

    }
    /**
     * Test case for ping command.
     */
    @Test(description = "Redis {ping} integration test.")
    public void testPing() throws IOException, JSONException {

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@   before call proxy............");

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("ping"), "POST",
                esbRequestHeadersMap);

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"PONG\"}");
    }
    /**
     * Test case for quit operation.
     */
    @Test(description = "Redis {quit} integration test.")
    public void testQuit() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("quit"), "POST",
                esbRequestHeadersMap);

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for set operation.
     */
    @Test(description = "Redis {set} integration test.")
    public void testSet() throws IOException, JSONException {

        sendJsonRestRequest(getProxyServiceURL("set"), "POST", esbRequestHeadersMap, "set.json");

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("exists"), "POST",
                esbRequestHeadersMap, "exists.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"true\"}");
    }
    /**
     * Test case for randomKey operation.
     */
    @Test(priority =  1,  description = "Redis {randomKey} integration test.")
    public void testRandomKey() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("randomKey"), "POST", esbRequestHeadersMap);

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\""+connectorProperties.getProperty("key")+"\"}");
    }
    /**
     * Test case for get operation.
     */
    @Test(priority =  1,  description = "Redis {get} integration test.")
    public void testGet() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("get"), "POST",
                esbRequestHeadersMap, "get.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\""+connectorProperties.getProperty
                ("value") +"\"}");
    }
    /**
     * Test case for type operation.
     */
    @Test(priority =  1,  description = "Redis {type} integration test.")
    public void testType() throws IOException, JSONException {

        testSet();
        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("type"), "POST", esbRequestHeadersMap, "exists.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"string\"}");
    }
    /**
     * Test case for substr operation.
     */
    @Test(priority =  1,  description = "Redis {substr} integration test.")
    public void testSubstr() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("substr"), "POST",
                esbRequestHeadersMap, "substr.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"ello\"}");
    }
    /**
     * Test case for keys operation.
     */
    @Test(priority =  1,  description = "Redis {keys} integration test.")
    public void testKeys() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("keys"), "POST",
                esbRequestHeadersMap, "keys.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"[mykey]\"}");
    }
    /**
     * Test case for getSet operation.
     */
    @Test(priority =  2,  description = "Redis {getSet} integration test.")
    public void testGetSet() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("getSet"), "POST",
                esbRequestHeadersMap, "getset.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"hello\"}");
    }
    /**
     * Test case for setnx operation.
     */
    @Test(priority =  2,  description = "Redis {setnx} integration test.")
    public void testSetnx() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("setnx"), "POST",
                esbRequestHeadersMap, "setnx.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for hset operation.
     */
    @Test(priority =  2,  description = "Redis {hset} integration test.")
    public void testHset() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hset"), "POST",
                esbRequestHeadersMap, "hset.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for hsetnx operation.
     */
    @Test(priority =  2,  description = "Redis {hsetnx} integration test.")
    public void testHsetnx() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hsetnx"), "POST",
                esbRequestHeadersMap, "hsetnx.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for lpush operation.
     */
    @Test(priority =  2,  description = "Redis {lpush} integration test.")
    public void testLpush() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("lpush"), "POST",
                esbRequestHeadersMap, "lpush.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     //     * Test case for mset command.
     //     */
    @Test(priority = 2, description = "Redis {mset} integration test.")
    public void testMset() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("mset"), "POST",
                esbRequestHeadersMap, "mset.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
    * Test case for msetnx command.
    */
    @Test(priority = 2, description = "Redis {msetnx} integration test.")
    public void testMsetnx() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("msetnx"), "POST",
                esbRequestHeadersMap, "msetnx.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for hmset command.
     */
    @Test(priority = 2, description = "Redis {hmset} integration test.")
    public void testHmset() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hmset"), "POST",
                esbRequestHeadersMap, "hmset.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for rename operation.
     */
    @Test(priority =  3,  description = "Redis {rename} integration test.")
    public void testRename() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("rename"), "POST",
                esbRequestHeadersMap, "rename.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for incrBy operation.
     */
    @Test(priority =  3,  description = "Redis {incrBy} integration test.")
    public void testIncrBy() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("incrBy"), "POST",
                esbRequestHeadersMap, "decrBy.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"13\"}");
    }
    /**
     * Test case for hget operation.
     */
    @Test(priority =  3,  description = "Redis {hget} integration test.")
    public void testHget() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hget"), "POST",
                esbRequestHeadersMap, "hget.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"myhashvalue\"}");
    }
    /**
     * Test case for hexists operation.
     */
    @Test(priority =  3,  description = "Redis {hexists} integration test.")
    public void testHexists() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hexists"), "POST",
                esbRequestHeadersMap, "hget.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"true\"}");
    }
    /**
     * Test case for hincrBy operation.
     */
    @Test(priority =  3,  description = "Redis {hincrBy} integration test.")
    public void testHincrBy() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hincrBy"), "POST",
                esbRequestHeadersMap, "hincrBy.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"8\"}");
    }
    /**
     * Test case for hlen operation.
     */
    @Test(priority =  3,  description = "Redis {hlen} integration test.")
    public void testHlen() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hlen"), "POST",
                esbRequestHeadersMap, "hlen.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"2\"}");
    }
    /**
     * Test case for llen operation.
     */
    @Test(priority =  3,  description = "Redis {llen} integration test.")
    public void testLlen() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("llen"), "POST",
                esbRequestHeadersMap, "llen.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for hmget operation.
     */
    @Test(priority =  3,  description = "Redis {hmget} integration test.")
    public void testHmget() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hmget"), "POST",
                esbRequestHeadersMap, "hmget.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"hello\"}");
    }
    /**
     * Test case for mget operation.
     */
    @Test(priority =  3,  description = "Redis {mget} integration test.")
    public void testMget() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("mget"), "POST",
                esbRequestHeadersMap, "mget.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"[a, b, c]\"}");
    }
    /**
     * Test case for hkeys operation.
     */
    @Test(priority =  6,  description = "Redis {hkeys} integration test.")
    public void testHkeys() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hkeys"), "POST",
                esbRequestHeadersMap, "hkeys.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"[myhashfield1, myhashfield]\"}");
    }
    /**
     * Test case for hvals operation.
     */
    @Test(priority =  3,  description = "Redis {hvals} integration test.")
    public void testHvals() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hvals"), "POST",
                esbRequestHeadersMap, "hvals.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"[myhashvalue, 8]\"}");
    }
    /**
     * Test case for renamenx operation.
     */
    @Test(priority =  4,  description = "Redis {renamenx} integration test.")
    public void testRenamenx() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("rename"), "POST",
                esbRequestHeadersMap, "renamenx.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
        testSet();
    }
    /**
     * Test case for decrBy operation.
     */
    @Test(priority =  4,  description = "Redis {decrBy} integration test.")
    public void testDecrBy() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("decrBy"), "POST",
                esbRequestHeadersMap, "decrBy.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"10\"}");
    }
    /**
     * Test case for lset operation.
     */
    @Test(priority =  4,  description = "Redis {lset} integration test.")
    public void testLset() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("lset"), "POST",
                esbRequestHeadersMap, "lset.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for hdel operation.
     */
    @Test(priority =  4,  description = "Redis {hdel} integration test.")
    public void testHdel() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("hdel"), "POST",
                esbRequestHeadersMap, "hdel.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for append operation.
     */
    @Test(priority =  5,  description = "Redis {append} integration test.")
    public void testAppend() throws IOException, JSONException {

        RestResponse<JSONObject> appendResponse = sendJsonRestRequest(getProxyServiceURL("append"), "POST",
                esbRequestHeadersMap, "append.json");
        RestResponse<JSONObject> strlenResponse = sendJsonRestRequest(getProxyServiceURL("strlen"), "POST",
                esbRequestHeadersMap, "strlen.json");

        Assert.assertEquals(appendResponse.getBody().toString(),strlenResponse.getBody().toString());
    }
    /**
     * Test case for expire operation.
     */
    @Test(priority =  5,  description = "Redis {expire} integration test.")
    public void testExpire() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("expire"), "POST",
                esbRequestHeadersMap, "expire.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for ltrim operation.
     */
    @Test(priority =  5,  description = "Redis {ltrim} integration test.")
    public void testLtrim() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("ltrim"), "POST",
                esbRequestHeadersMap, "ltrim.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for rpush operation.
     */
    @Test(priority =  5,  description = "Redis {rpush} integration test.")
    public void testRpush() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("rpush"), "POST",
                esbRequestHeadersMap, "rpush.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"3\"}");
    }
    /**
     * Test case for expireAt operation.
     */
    @Test(priority =  6,  description = "Redis {expireAt} integration test.")
    public void testExpireAt() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("expireAt"), "POST",
                esbRequestHeadersMap, "expireAt.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
//    /**
//     * Test case for ttl operation.
//     */
//    @Test(priority =  6,  description = "Redis {ttl} integration test.")
//    public void testTtl() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("ttl"), "POST",
//                esbRequestHeadersMap, "exists.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
//    }
    /**
     * Test case for lrem operation.
     */
    @Test(priority =  6,  description = "Redis {lrem} integration test.")
    public void testLrem() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("lrem"), "POST",
                esbRequestHeadersMap, "lrem.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for sadd operation.
     */
    @Test(priority =  6,  description = "Redis {sadd} integration test.")
    public void testSadd() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",
                esbRequestHeadersMap, "sadd.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"5\"}");
    }
    /**
     * Test case for lpop operation.
     */
    @Test(priority =  7,  description = "Redis {lpop} integration test.")
    public void testLpop() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("lpop"), "POST",
                esbRequestHeadersMap, "lpop.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"list2\"}");
    }
//    /**
//     * Test case for smembers operation.
//     */
//    @Test(priority =  7,  description = "Redis {smembers} integration test.")
//    public void testSmembers() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("smembers"), "POST",
//                esbRequestHeadersMap, "smembers.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"5\"}");
//    }
    /**
     * Test case for zadd operation.
     */
    @Test(priority =  7,  description = "Redis {zadd} integration test.")
    public void testZadd() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zadd"), "POST",
                esbRequestHeadersMap, "zadd.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
//    /**
//     * Test case for zrem operation.
//     */
//    @Test(priority =  7,  description = "Redis {zrem} integration test.")
//    public void testZrem() throws IOException, JSONException {
//
//        sendJsonRestRequest(getProxyServiceURL("zadd"), "POST", esbRequestHeadersMap, "zaddrem.json");
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zrem"), "POST",
//                esbRequestHeadersMap, "zrem.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
//    }
    /**
     * Test case for rpoplpush operation.
     */
    @Test(priority =  8,  description = "Redis {rpoplpush} integration test.")
    public void testRpoplpush() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("rpoplpush"), "POST",
                esbRequestHeadersMap, "rpoplpush.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"hello\"}");
    }
    /**
     * Test case for srem operation.
     */
    @Test(priority =  8,  description = "Redis {srem} integration test.")
    public void testSrem() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("srem"), "POST",
                esbRequestHeadersMap, "srem.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
    /**
     * Test case for smove operation.
     */
    @Test(priority =  8,  description = "Redis {smove} integration test.")
    public void testSmove() throws IOException, JSONException {

        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddremove1.json");
        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddremove2.json");

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("smove"), "POST",
                esbRequestHeadersMap, "smove.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
//    /**
//     * Test case for zrange operation.
//     */
//    @Test(priority =  8,  description = "Redis {zrange} integration test.")
//    public void testZrange() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zrange"), "POST",
//                esbRequestHeadersMap, "zrange.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"zmember\"}");
//    }
    /**
     * Test case for zincrby operation.
     */
    @Test(priority =  8,  description = "Redis {zincrby} integration test.")
    public void testZincrby() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zincrby"), "POST",
                esbRequestHeadersMap, "zadd.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"2.2\"}");
    }
    /**
     * Test case for zrevrank operation.
     */
    @Test(priority =  8,  description = "Redis {zrevrank} integration test.")
    public void testZrevrank() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zrevrank"), "POST",
                esbRequestHeadersMap, "zrank.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"0\"}");
    }
    /**
     * Test case for zrank operation.
     */
    @Test(priority =  8,  description = "Redis {zrank} integration test.")
    public void testZrank() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zrank"), "POST",
                esbRequestHeadersMap, "zrank.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"0\"}");
    }
    /**
     * Test case for del operation.
     */
    @Test(priority =  9,  description = "Redis {del} integration test.")
    public void testDel() throws IOException, JSONException {

        sendJsonRestRequest(getProxyServiceURL("del"), "POST", esbRequestHeadersMap, "exists.json");

        RestResponse<JSONObject> existsResponse = sendJsonRestRequest(getProxyServiceURL("exists"), "POST",
                esbRequestHeadersMap, "exists.json");

        Assert.assertEquals(existsResponse.getBody().toString(), "{\"output\":\"false\"}");
    }
    /**
     * Test case for sismember operation.
     */
    @Test(priority =  9,  description = "Redis {sismember} integration test.")
    public void testSismember() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("sismember"), "POST",
                esbRequestHeadersMap, "sismember.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"true\"}");
    }
//    /**
//     * Test case for sunion operation.
//     */
//    @Test(priority =  9,  description = "Redis {sunion} integration test.")
//    public void testSunion() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("sunion"), "POST",
//                esbRequestHeadersMap, "sunion.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"true\"}");
//    }
//    /**
//     * Test case for zrevrange operation.
//     */
//    @Test(priority =  9,  description = "Redis {zrevrange} integration test.")
//    public void testZrevrange() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zrevrange"), "POST",
//                esbRequestHeadersMap, "sismember.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"true\"}");
//    }
    /**
     * Test case for zscore operation.
     */
    @Test(priority =  9,  description = "Redis {zscore} integration test.")
    public void testZscore() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zscore"), "POST",
                esbRequestHeadersMap, "zrank.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"2.2\"}");
    }
    /**
     * Test case for zcount operation.
     */
    @Test(priority =  9,  description = "Redis {zcount} integration test.")
    public void testZcount() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("zcount"), "POST",
                esbRequestHeadersMap, "zcount.json");

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"1\"}");
    }
//    /**
//    * Test case for spop operation.
//    */
//    @Test(priority =  9,  description = "Redis {spop} integration test.")
//    public void testSpop() throws IOException, JSONException {
//
//        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddpop.json");
//        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddpop1.json");
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("spop"), "POST",
//                esbRequestHeadersMap, "spop.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"a\"}");
//    }
//    /**
//     * Test case for sinter operation.
//     */
//    @Test(priority =  10,  description = "Redis {sinter} integration test.")
//    public void testSinter() throws IOException, JSONException {
//
//        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddpop.json");
//        sendJsonRestRequest(getProxyServiceURL("sadd"), "POST",esbRequestHeadersMap, "saddpop1.json");
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("sinter"), "POST",
//                esbRequestHeadersMap, "sinter.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"a\"}");
//    }
//    /**
//     * Test case for sinterstore operation.
//     */
//    @Test(priority =  11,  description = "Redis {sinterstore} integration test.")
//    public void testSinterstore() throws IOException, JSONException {
//
//        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("sinterstore"), "POST",
//                esbRequestHeadersMap, "sinterstore.json");
//
//        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
//    }
    /**
     * Test case for flushDB operation.
     */
    @Test(priority =  15,  description = "Redis {flushDB} integration test.")
    public void testFlushDB() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("flushDB"), "POST", esbRequestHeadersMap);

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }
    /**
     * Test case for flushAll operation.
     */
    @Test(priority =  15,  description = "Redis {flushAll} integration test.")
    public void testFlushAll() throws IOException, JSONException {

        RestResponse<JSONObject> response = sendJsonRestRequest(getProxyServiceURL("flushAll"), "POST", esbRequestHeadersMap);

        Assert.assertEquals(response.getBody().toString(), "{\"output\":\"OK\"}");
    }









}
