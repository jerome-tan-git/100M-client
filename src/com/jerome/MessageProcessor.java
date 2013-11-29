package com.jerome;

import java.io.IOException;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MessageProcessor {
	private final Producer<Integer, String> producer;

	public MessageProcessor() throws IOException {
		Properties props = new Properties();
		props.put("metadata.broker.list", SystemProperties.getInstance().getProperty("kafka.broker.list"));

		props.put("serializer.class", SystemProperties.getInstance()
				.getProperty("kafka.serializer.class"));
		props.put("zk.connectiontimeout.ms", SystemProperties.getInstance()
				.getProperty("kafka.zookeeper.connection.timeout.ms"));
		ProducerConfig config = new ProducerConfig(props);
		props.put("request.required.acks", "1");
		producer = new Producer<Integer, String>(config);
	}

	public void processMessage(String _msg) {
		System.out.println("Get message: " + _msg);
		String messageStr = new String("Message_" + _msg);
		producer.send(new KeyedMessage<Integer, String>("mytest", messageStr));
	}
	public void close()
	{
		if(this.producer !=null)
		{
			this.producer.close();
		}
	}
}
