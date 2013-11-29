package com.jerome;

import java.io.IOException;

public class MsgProcessorStart {
	public static void main(String[] args) throws IOException {
		// Init Zookeeper servers list
		ZookeeperConnector zkConnector = new ZookeeperConnector();
		// Init HBase connection
		HBaseConnector hbase = HBaseConnector.getInstance();
		// Init kafka connection
		MessageReceiver mr = new MessageReceiver();
		mr.run();
		
		
	}
}
