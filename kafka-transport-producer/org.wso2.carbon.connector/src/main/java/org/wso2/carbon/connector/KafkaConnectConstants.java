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

package org.wso2.carbon.connector;

public class KafkaConnectConstants {
	
	// Configuration properties keys
	
	public static final String BROKER_LIST = "metadata.broker.list";
	public static final String REQUIRED_ACKS = "request.required.acks";
	public static final String PRODUCER_TYPE = "producer.type";
	public static final String SERIALIZATION_CLASS = "serializer.class";
	public static final String KEY_SERIALIZER_CLASS = "key.serializer.class";
	public static final String PARTITION_CLASS = "partitioner.class";
	public static final String COMPRESSION_TYPE = "compression.codec";
	public static final String COMPRESSED_TOPIC = "compressed.topics";
	public static final String MESSAGE_SEND_MAX_RETRIES = "message.send.max.retries";
	public static final String TIME_REFRESH_METADATA = "retry.backoff.ms";
	public static final String TIME_REFRESH_METADTA_AFTER_TOPIC = "topic.metadata.refresh.interval.ms";
	public static final String BUFFER_MAX_TIME = "queue.buffering.max.ms";
	public static final String BUFFER_MAX_MESSAGES = "queue.buffering.max.messages";
	public static final String NO_MESSAAGE_BATCHED_PRODUCER = "batch.num.messages";
	public static final String BUFFER_SIZE = "send.buffer.bytes";
	public static final String REQUEST_TIMEOUT = "request.timeout.ms";
	public static final String ENQUEUE_TIMEOUT = "queue.enqueue.timeout.ms";
	public static final String CLIENT_ID = "client.id";
	
	// Configuration properties parameter
	
	public static final String PARAM_TOPIC = "topic";
	public static final String PARAM_MESSAGE = "message";
	public static final String PARAM_KEY = "key";
	
}
