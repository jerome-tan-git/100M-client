package com.jerome;

import java.io.IOException;
import java.util.List;

import com.github.zkclient.ZkClient;

public class ZookeeperConnector {
	public ZookeeperConnector() throws IOException
	{
		ZkClient client = new ZkClient(SystemProperties.getInstance().getProperty("zookeeper.servers"), 15000, 15000);
		client.subscribeChildChanges(SystemProperties.getInstance().getProperty("zookeeper.servernode"), new ZKListener());
		List<String> servers = client.getChildren(SystemProperties.getInstance().getProperty("zookeeper.servernode"));
		ValidServers.getInstance().refreshList(servers);
	}
}
