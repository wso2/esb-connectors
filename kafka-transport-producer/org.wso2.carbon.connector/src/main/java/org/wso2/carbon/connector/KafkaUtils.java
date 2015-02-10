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

import java.io.IOException;

import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.MessageFormatter;
import org.apache.axis2.transport.base.BaseUtils;
import org.apache.axis2.util.MessageProcessorSelector;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

public class KafkaUtils {

	public static String lookupTemplateParamater(MessageContext ctxt,
			String paramName) {
		return (String) ConnectorUtils.lookupTemplateParamater(ctxt, paramName);
	}

	public static Producer<String, String> getProducer(MessageContext ctxt) {

		Axis2MessageContext axis2mc = (Axis2MessageContext) ctxt;
		axis2mc.getAxis2MessageContext();
		String brokers = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.brokerlist");
		String serializationClass = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.serializationClass");
		String requiredAcks = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.requiredAcks");
		String producerType = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.producertype");
		String compressionCodec = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.compressioncodec");
		String keySerializerClass = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.keyserializerclass");
		String partitionerClass = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.partitionerclass");
		String compressedTopics = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.compressedtopics");
		String messagesendMaxRetries = (String) axis2mc
				.getAxis2MessageContext().getOperationContext()
				.getProperty("kafka.messagesendmaxretries");
		String retryBackoff = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.retrybackoff");
		String refreshInterval = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.refreshinterval");
		String bufferingMaxMessages = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext()
				.getProperty("kafka.bufferingmaxmessages");
		String batchNoMessages = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.batchnomessages");
		String sendBufferSize = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.sendbuffersize");
		String requestTimeout = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.requestTimeout");
		String bufferingmaxtime = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.bufferingmaxtime");
		String enqueueTimeout = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.enqueueTimeout");
		String clientId = (String) axis2mc.getAxis2MessageContext()
				.getOperationContext().getProperty("kafka.clientId");

		Properties prop = new Properties();
		prop.put(KafkaConnectConstants.BROKER_LIST, brokers);
		prop.put(KafkaConnectConstants.SERIALIZATION_CLASS, serializationClass);
		prop.put(KafkaConnectConstants.REQUIRED_ACKS, requiredAcks);
		prop.put(KafkaConnectConstants.PRODUCER_TYPE, producerType);
		prop.put(KafkaConnectConstants.COMPRESSION_TYPE, compressionCodec);
		prop.put(KafkaConnectConstants.KEY_SERIALIZER_CLASS, keySerializerClass);
		prop.put(KafkaConnectConstants.PARTITION_CLASS, partitionerClass);
		prop.put(KafkaConnectConstants.COMPRESSED_TOPIC, compressedTopics);
		prop.put(KafkaConnectConstants.MESSAGE_SEND_MAX_RETRIES,
				messagesendMaxRetries);
		prop.put(KafkaConnectConstants.TIME_REFRESH_METADATA, retryBackoff);
		prop.put(KafkaConnectConstants.TIME_REFRESH_METADTA_AFTER_TOPIC,
				refreshInterval);
		prop.put(KafkaConnectConstants.BUFFER_MAX_MESSAGES,
				bufferingMaxMessages);
		prop.put(KafkaConnectConstants.NO_MESSAAGE_BATCHED_PRODUCER,
				batchNoMessages);
		prop.put(KafkaConnectConstants.BUFFER_SIZE, sendBufferSize);
		prop.put(KafkaConnectConstants.REQUEST_TIMEOUT, requestTimeout);
		prop.put(KafkaConnectConstants.BUFFER_MAX_TIME, bufferingmaxtime);
		prop.put(KafkaConnectConstants.ENQUEUE_TIMEOUT, enqueueTimeout);
		prop.put(KafkaConnectConstants.CLIENT_ID, clientId);

		return new Producer<String, String>(new ProducerConfig(prop));
	}

	public static String formateMessage(
			org.apache.axis2.context.MessageContext ctxt) throws AxisFault {
		OMOutputFormat format = BaseUtils.getOMOutputFormat(ctxt);
		MessageFormatter messageFormatter = null;
		messageFormatter = MessageProcessorSelector.getMessageFormatter(ctxt);
		OutputStream out = null;
		StringWriter sw = null;
		sw = new StringWriter();
		out = new WriterOutputStream(sw, format.getCharSetEncoding());
		try {
			if (out != null) {
				messageFormatter.writeTo(ctxt, format, out, true);
				out.close();
			}
		} 
		catch (IOException e) {
		}

		return sw.toString();

	}

}
