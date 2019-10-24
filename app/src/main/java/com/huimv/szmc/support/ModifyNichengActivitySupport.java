package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;


public class ModifyNichengActivitySupport {
    /** 修改昵称线程 **/
    public static void ModifyNichengThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_YHNC_UPDATE, param, "",
                XtAppConstant.WHAT_MODIFY_NICHENG_THREAD, handler, context).start();
    }
}
