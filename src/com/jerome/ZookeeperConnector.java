package com.jerome;

import java.io.IOException;

import com.github.zkclient.ZkClient;

public class ZookeeperConnector {
	public ZookeeperConnector() throws IOException
	{
		ZkClient client = new ZkClient(SystemProperties.getInstance().getProperty("zookeeper.servers"), 15000, 15000);
		client.subscribeChildChanges(SystemProperties.getInstance().getProperty("zookeeper.servermode"), new ZKListener());
	}
}
