package com.jerome.test;

import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.jerome.MessageObject;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class StartProducer {

	public static void main(String[] args) throws InterruptedException {
		kafka.javaapi.producer.Producer<Integer, String> producer;
		String topic;
		Properties props = new Properties();
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("metadata.broker.list",
				"BITEST10.VCLK.NET:9092,BITEST12.VCLK.NET:9092");

		producer = new kafka.javaapi.producer.Producer<Integer, String>(
				new ProducerConfig(props));
		int messageNo = 0;
		for (int i = 0; i < 100; i++) {
			MessageObject mo = new MessageObject();
			mo.setMsgID("messageID");
			mo.setFromUser("fromUser");
			mo.setToUser("123456");
			mo.setMessage("mess:a'ge b\"ody");
			String messageStr = JSON.toJSONString(mo);

			producer.send(new KeyedMessage<Integer, String>("receive",
					messageStr));
			Thread.sleep(1000);
		}
		// Thread p = new Producer("mytest");
		// p.start();
	}
}
