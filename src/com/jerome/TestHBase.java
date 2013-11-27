package com.jerome;

import java.io.IOException;
import java.text.NumberFormat;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

public class TestHBase {
	public static void main(String[] args) throws IOException {
		HBaseConnector hc = new HBaseConnector();
		long start = System.currentTimeMillis();
//		for(int i=0;i<10000;i++)
//		{
//			hc.addUserMessage("a", "ca", "b");
//		}
//		System.out.println(System.currentTimeMillis() - start);
//		start = System.currentTimeMillis();
		hc.batchRemoveMessage("a", 1385543855470L, 1385543855470L);
		ResultScanner ResultScannerFilterList = hc.getAllUserMessage("a");
		int count = 0;
        for(Result rr=ResultScannerFilterList.next();rr!=null;rr=ResultScannerFilterList.next()){
        	count++;
            for(KeyValue kv:rr.list()){  
                System.out.println("row : "+new String(kv.getRow()));  
                System.out.println("column : "+new String(kv.getFamily()));  
                System.out.println("value : "+new String(kv.getValue()));  
            }  
        } 
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(count);
//		hc.setServerIDByUserID("a", "aaaaaaaaaa");
//		System.out.println(hc.getServerIDByUserID("a"));
//		hc.addUserMessage("a", "ca", "b");
//		hc.getUserMessage(_userID, _fromTime, _toTime)
//		System.out.println((System.currentTimeMillis()+"").length());
//		int number = 11;     
//		NumberFormat formatter = NumberFormat.getNumberInstance();     
//		formatter.setMinimumIntegerDigits(13);     
//		formatter.setGroupingUsed(false);     
//		String s = formatter.format(number);     
		              
//		System.out.println(s); 
	}
}
