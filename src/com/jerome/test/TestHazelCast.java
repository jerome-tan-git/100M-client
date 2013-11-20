package com.jerome.test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class TestHazelCast {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("192.168.103.18:5701");
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		IMap map = client.getMap("customers");
		long a = System.currentTimeMillis();
		for (int i=0;i<100000;i++)
		{
			map.put(""+i, "1");
		}
		System.out.println("Cost: " + (System.currentTimeMillis() -a));
		System.out.println("Map Size:" + map.size());
		client.shutdown();
	}
}
