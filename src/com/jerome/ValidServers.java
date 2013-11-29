package com.jerome;

import java.util.HashSet;
import java.util.List;

public class ValidServers {
	private static ValidServers servers;
	private HashSet<String> serverList = new HashSet<String>();
	public static ValidServers getInstance()
	{
		if(ValidServers.servers==null)
			ValidServers.servers = new ValidServers();
		return ValidServers.servers;
	}
	public ValidServers()
	{
		
	}
	
	public void refreshList(List<String> _server)
	{
		HashSet<String> newServerList = new HashSet<String>();
		for(String server: _server)
		{
			newServerList.add(server);
		}
		this.serverList = newServerList;
	}
	
	public boolean serverOnline(String _server)
	{
		return this.serverList.contains(_server);
	}
	
}
