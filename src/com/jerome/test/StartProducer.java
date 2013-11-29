package com.jerome.test;

import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class StartProducer {
	
	public static void main(String[] args) {
		kafka.javaapi.producer.Producer<Integer, String> producer;
		String topic;
		Properties props = new Properties();
		props.put("serializer.class", "kafka.serializer.StringEncoder");
	    props.put("metadata.broker.list", "BITEST10.VCLK.NET:9092,BITEST12.VCLK.NET:9092");

	    producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
	    int messageNo =0;
	    String messageStr = new String("Message_" + messageNo);
	    producer.send(new KeyedMessage<Integer, String>("receive", messageStr));
//		Thread p = new Producer("mytest");
//		p.start();
	}
}
