package com.jerome.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.jerome.MessageObject;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class StartConsumer {
	public static void main(String[] args) {
		// Thread a= new Consumer("mytest");
		// a.start();
		ConsumerConnector consumer;
		String topic = "receive_ServerA";
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.103.5");
		props.put("group.id", KafkaProperties.groupId);
		props.put("zookeeper.session.timeout.ms", "14000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(new ConsumerConfig(props));
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {
			String getMessage = new String(it.next().message());
			MessageObject o = null;
			try {
				o = JSON.parseObject(getMessage, MessageObject.class);
				String id = o.getMsgID();
				MessageObject confirmObj = new MessageObject();
				confirmObj.setFromServer("ServerA");
				confirmObj.setMessageType("confirm");
				confirmObj.setMessage(id);
				confirmObj.setToUser(o.getFromUser());
				confirmObj.setFromUser(o.getToUser());
				JSON.toJSONString(confirmObj);
			} catch (Exception e) {
				System.out.println("Invalid message format: " + e.getMessage());
			}
			if(o!=null)
			{
				System.out.println(getMessage);
			}
			
		}
	}

}
