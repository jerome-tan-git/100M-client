package com.jerome;

import java.util.List;

import com.github.zkclient.IZkChildListener;

public class ZKListener implements IZkChildListener {

	@Override
	public void handleChildChange(String path, List<String> children)
			throws Exception {
			ValidServers.getInstance().refreshList(children);
			System.out.println(children);
	}

}
