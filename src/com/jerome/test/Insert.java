package com.jerome.test;
import java.util.UUID;

import com.github.zkclient.ZkClient;

public class Insert {
	public static void main(String[] args) {
		ZkClient client = new ZkClient("192.168.103.5:" + 2181, 15000, 15000);
		if(!client.exists("/workers"))
		{
			client.createPersistent("/workers");
		}
		client.createEphemeral("/workers/" + UUID.randomUUID());
		
	}
}
