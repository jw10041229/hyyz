package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;


public class ResetPasswordActivitySupport {
    /** 找回密码线程 **/
    public static void ResetPasswordThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_YHMM_RESET, param, "",
                XtAppConstant.WHAT_RESET_PASSWORD_THREAD, handler, context).start();
    }
}
