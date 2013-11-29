package com.jerome;

import java.io.IOException;

public class TestKafka {
	public static void main(String[] args) throws IOException {
		MessageReceiver mr = new MessageReceiver();
		mr.run();
	}
}
