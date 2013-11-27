package com.jerome;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

public class HBaseConnectionPool {
	private static Configuration conf = null;
	static {
		conf = HBaseConfiguration.create();
	}
	private static HTablePool hTablePool = null;

	public static synchronized HTableInterface getHtable(String tableName) {
		if (hTablePool != null)
			return hTablePool.getTable(tableName);
		else {
			hTablePool = new HTablePool(conf, 20);
//			hTablePool.
			return hTablePool.getTable(tableName);
		}
	}
}
