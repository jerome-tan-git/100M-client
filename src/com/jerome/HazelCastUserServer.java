package com.jerome;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelCastUserServer implements IFindUserServer{
	private IMap<String, String> map;
	public HazelCastUserServer(String _server, String _tableName)
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("192.168.103.18:5701");
		HazelcastInstance client = HazelcastClient
				.newHazelcastClient(clientConfig);
		map = client.getMap("customers");
	}
	@Override
	public String findServer(String _userID) {
		if (map!=null)
		{
			return map.get(_userID);
		}
		return null;
	}

}
