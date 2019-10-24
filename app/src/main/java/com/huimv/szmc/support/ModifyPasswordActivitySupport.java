package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;

public class ModifyPasswordActivitySupport {
	/** 修改密码线程 **/
	public static void ModifyPasswordThread(String param, Context context, Handler handler) {
		new HaifmServiceThread(WsCmd.HAIFM_YHMM_UPDATE, param, "", XtAppConstant.WHAT_MODIFY_PASSWORD_THREAD, handler,
				context).start();
	}
}
