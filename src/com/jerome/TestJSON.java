package com.jerome;

import com.alibaba.fastjson.JSON;

public class TestJSON {
	public static void main(String[] args) {
		MessageObject mo = new MessageObject();
		mo.setMsgID("messageID");
		mo.setFromUser("fromUser");
		mo.setToUser("toUser");
		mo.setMessage("mess:a'ge b\"ody");
		mo.setMessageType("1");
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++) {
//			JSON.toJSONString(mo);
//		}
//		System.out.println(System.currentTimeMillis() - start);
		System.out.println(JSON.toJSONString(mo));
		
	}
}
