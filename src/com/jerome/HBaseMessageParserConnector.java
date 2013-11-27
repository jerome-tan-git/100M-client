package com.jerome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseMessageParserConnector {
	private static Configuration conf = null;
	static {
		conf = HBaseConfiguration.create();
	}

	public static void main(String[] args) throws IOException {
		// System.out.println(1);
		// HBaseAdmin admin = new HBaseAdmin(conf);
		// System.out.println(2);
		// if (admin.tableExists("a")) {
		// System.out.println("表已经存在！");
		// } else {
		// System.out.println("aaaaaaaaaaa");
		// }

		
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++)
		{
			HTableInterface table = HBaseConnectionPool.getHtable("test");
		Get g = new Get(Bytes.toBytes(""+i));
		
		Result a = table.get(g);
		table.close();
		}
//		System.out.println(new String(a.getValue(Bytes.toBytes("cf"), Bytes.toBytes("test"))));
//		for (int i = 0; i < 10000; i++) {
//			Put p = new Put(Bytes.toBytes(""+i));
//			p.add(Bytes.toBytes("cf"), Bytes.toBytes("test"),
//					Bytes.toBytes("va"+i));
//			table.put(p);
//		}
		System.out.println(System.currentTimeMillis() - start);
		// System.out.println(2);
		


	}
	// private String tableName;
	//
	// public HBaseMessageParserConnector(String _tableName) {
	// this.tableName = _tableName;
	// }
	//
	// @Override
	// // message format key|cf1:col1:value1|cf2:col2:value2
	// public Element getElement(String _message) {
	//
	// String[] keyValue = _message.split("\\|");
	//
	// if (keyValue.length > 1) {
	// String key = keyValue[0];
	// List<HBasePair> values = new ArrayList<HBasePair>();
	// Element e = new Element(key, values, _message);
	// int valueSize = keyValue.length;
	// for (int i = 1; i < valueSize; i++) {
	// String value = keyValue[i];
	// String[] valueElement = value.split("\\:");
	// if (valueElement.length >= 3) {
	// String cf = valueElement[0];
	// String col = valueElement[1];
	// String v = valueElement[2];
	// if (cf.trim().equals("")) {
	// continue;
	// } else {
	// HBasePair pair = new HBasePair(cf, col, v);
	// values.add(pair);
	// }
	// } else {
	// continue;
	// }
	//
	// }
	// if (values.size() > 0) {
	// return e;
	// } else {
	// return null;
	// }
	// }
	// return null;
	// }
	//
	// @Override
	// public int put(Element _e) {
	//
	// String key = _e.getKey();
	//
	// @SuppressWarnings("unchecked")
	// List<HBasePair> values = (List<HBasePair>) _e.getValues();
	// Put p = new Put(Bytes.toBytes(key));
	// for (HBasePair pair : values) {
	// p.add(Bytes.toBytes(pair.getCf()), Bytes.toBytes(pair.getCol()),
	// Bytes.toBytes(pair.getValue()));
	// }
	// HTableInterface table = HBaseConnectionPool.getHtable(this.tableName);
	// try {
	// table.put(p);
	// // System.out.println(_e);
	// table.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// // TODO log
	// return 1;
	// }
	// return 0;
	// }

}
