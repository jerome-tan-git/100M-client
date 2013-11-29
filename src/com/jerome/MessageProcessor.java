package com.jerome;

import java.io.IOException;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

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
		ProducerConfig config = new ProducerConfig(props);
		props.put("request.required.acks", "1");
		producer = new Producer<Integer, String>(config);
	}

	public void processMessage(String _msg) {
		System.out.println("Get message: " + _msg);
		
		HBaseConnector hc =HBaseConnector.getInstance();
		if(hc != null)
		{
			MessageObject o= null;
			try {
				o = JSON.parseObject(_msg, MessageObject.class);
			} catch (Exception e) {
				System.out.println("Invalid message format: " + e.getMessage());
			}
			if(o!=null)
			{
				String msgID = hc.addUserMessage(o.getToUser(), o.getMessage(), o.getFromUser());
				o.setMsgID(msgID);
				if(msgID != null)
				{
					String serverID = hc.getServerIDByUserID(o.getToUser());
					if(ValidServers.getInstance().serverOnline(serverID))
					{
						String backMessage = JSON.toJSONString(o);						
					    producer.send(new KeyedMessage<Integer, String>("receive_"+serverID, backMessage));
					    System.out.println("Message sent: " + "receive_"+serverID + " | " + backMessage);
					}
					else
					{
						System.out.println("No server found for userID: " + o.getToUser());
					}
				}
			}
		}
		
	}
	public void close()
	{
		if(this.producer !=null)
		{
			this.producer.close();
		}
	}
}
