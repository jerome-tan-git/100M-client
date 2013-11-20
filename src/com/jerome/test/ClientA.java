package com.jerome.test;
import com.github.zkclient.IZkChildListener;
import com.github.zkclient.ZkClient;

public class ClientA {
	public static void main(String[] args) {
		ZkClient client = new ZkClient("192.168.103.5:2181", 15000, 15000);
		client.subscribeChildChanges("/workers", new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath,
					java.util.List currentChilds) throws Exception {
				System.out.println("Children count: " + currentChilds.size());
				System.out.println("==================================================");
				for (int i = 0; i < currentChilds.size(); i++) {
					System.out.println(currentChilds.get(i));
				}
				System.out.println("==================================================");
			}
		});
		try {
			Thread.sleep(30000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
