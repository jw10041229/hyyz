package com.huimv.szmc.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


import com.huimv.szmc.client.HaifmServiceClient;
import com.huimv.szmc.client.ResultSet;

import java.util.HashMap;
import java.util.Map;

public class HaifmServiceThread extends Thread {
	private int what;
	private Handler handler;
	private Context context;
	private Map<String,String> parmasMap = new HashMap<String, String>();
	public HaifmServiceThread(String cmd, String params, String moreStr, int what,
			Handler handler, Context context) {
		this.what = what;
		this.handler = handler;
		this.context = context;
		parmasMap.put("cmd", cmd);
		parmasMap.put("params", params);
	}

	@Override
	public void run() {
        HaifmServiceClient client = new HaifmServiceClient();
		try {
			ResultSet rs = client.HaifmService(parmasMap, context);
			Message msg = handler.obtainMessage();
			msg.obj = rs.getStrJson();
            msg.arg1 = rs.getResultCode().getValue();
			msg.what = what;
			handler.sendMessage(msg);
		} catch (Exception e) {
		}
	}
}