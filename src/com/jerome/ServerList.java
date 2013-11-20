package com.jerome;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerList {
	private ConcurrentHashMap<String, ServerObject> serverList = new ConcurrentHashMap<String, ServerObject>();
	public ServerList()
	{
		
	}
	public boolean isServerOnline(String _serverID)
	{
		return this.serverList.contains(_serverID);
	}
	
	public void addServer(String _serverID, ServerObject _obj)
	{
		this.serverList.put(_serverID, _obj);
	}
	
//	public void addServers(List<String> _serverIDs)
//	{
//		if(_serverIDs !=null && _serverIDs.size()>0)
//		{
//			for(String serverID:_serverIDs)
//			{
//				this.serverList.put(serverID, null);
//			}
//		}
//	}
	
//	public 
	
}
