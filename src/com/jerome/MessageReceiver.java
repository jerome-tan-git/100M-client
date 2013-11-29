package com.jerome;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class MessageReceiver {
	private final ConsumerConnector consumer;
	private final String topic;
	private final int timeout;

	public MessageReceiver() throws IOException {
		consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(createConsumerConfig());
		this.topic = SystemProperties.getInstance().getProperty(
				"kafka.receive.topic");
		this.timeout = Integer.parseInt(SystemProperties.getInstance()
				.getProperty("kafka.consumer.timeout.ms"));
	}

	private ConsumerConfig createConsumerConfig() throws IOException {
		Properties props = new Properties();

		props.put("zookeeper.connect", SystemProperties.getInstance()
				.getProperty("kafka.zookeeper"));
		props.put(
				"group.id",
				SystemProperties.getInstance().getProperty(
						"kafka.receiver.groupid"));

		props.put("zookeeper.session.timeout.ms", SystemProperties
				.getInstance().getProperty("kafka.session.timeout"));
		props.put("zookeeper.sync.time.ms", SystemProperties.getInstance()
				.getProperty("kafka.sync.time"));
		props.put("auto.commit.interval.ms", SystemProperties.getInstance()
				.getProperty("kafka.auto.comit"));
		props.put("consumer.timeout.ms", SystemProperties.getInstance()
				.getProperty("kafka.consumer.timeout.ms"));
		return new ConsumerConfig(props);

	}

	public void run() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		try {
			long start = System.currentTimeMillis();
			while (it.hasNext()) {
				System.out.println(new String(it.next().message()));
				if ((System.currentTimeMillis() - start) > this.timeout) {
					throw new ConsumerTimeoutException();
				}
			}
		} catch (ConsumerTimeoutException e) {
			consumer.shutdown();
		}

	}
}
