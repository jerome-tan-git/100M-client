package com.jerome.test;

import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.jerome.MessageObject;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class Producer extends Thread
{
  private final kafka.javaapi.producer.Producer<Integer, String> producer;
  private final String topic;
  private final Properties props = new Properties();

  public Producer(String topic)
  {
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("metadata.broker.list", "BITEST10.VCLK.NET:9092,BITEST12.VCLK.NET:9092");

    producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
    this.topic = topic;
  }
  
  public void run() {
    int messageNo = 1;
    while(true)
    {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	MessageObject mo = new MessageObject();
		mo.setMsgID("messageID");
		mo.setFromUser("fromUser");
		mo.setToUser("UserA");
		mo.setMessage("mess:a'ge b\"ody");
      String messageStr = JSON.toJSONString(mo);
      producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
      messageNo++;
    }
  }

}