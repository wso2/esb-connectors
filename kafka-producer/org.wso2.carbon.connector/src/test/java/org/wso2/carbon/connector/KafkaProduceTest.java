/*
 *  Copyright (c) 2005-2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/*
package org.wso2.carbon.connector;
import java.util.Properties;
import junit.framework.TestCase;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

public class KafkaProduceTest extends TestCase {

	public static String message1 = "{\"message\":\"my sample message\"}";

	public void testConnection() throws Exception {

		Properties props = getProperties("localhost:9092",
				"kafka.serializer.StringEncoder", "1", "sync");
		Producer<String, String> producer = new Producer<String, String>(
				new ProducerConfig(props));
		KafkaProduce.send(producer, "test", "", message1);
	}

	private Properties getProperties(String brokerlist,
			String serializationClass, String requiredAcks, String producerType) {
		Properties props = new Properties();
		props.put("metadata.broker.list", brokerlist);
		props.put("serializer.class", serializationClass);
		props.put("request.required.acks", requiredAcks);
		props.put("producer.type", producerType);

		return props;
	}

}
*/
