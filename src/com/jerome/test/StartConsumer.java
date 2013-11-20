package com.jerome.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class StartConsumer {
	public static void main(String[] args) {
		// Thread a= new Consumer("mytest");
		// a.start();
		ConsumerConnector consumer;
		String topic="mytest";
		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.103.5");
		props.put("group.id", KafkaProperties.groupId);
		props.put("zookeeper.session.timeout.ms", "14000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	    topicCountMap.put(topic, new Integer(1));
	    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
	    KafkaStream<byte[], byte[]> stream =  consumerMap.get(topic).get(0);
	    ConsumerIterator<byte[], byte[]> it = stream.iterator();
	    while(it.hasNext())
	      System.out.println(new String(it.next().message()));
	  }
	
}
