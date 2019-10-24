package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;


public class RegisterActivitySupport {
    /** 注册线程 **/
    public static void RegisterThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_CZYH_REGISTER_INSERT, param, "",
                XtAppConstant.WHAT_REGISTER_THREAD, handler, context).start();
    }

}
