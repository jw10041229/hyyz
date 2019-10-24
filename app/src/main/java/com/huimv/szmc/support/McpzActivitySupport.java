package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;


public class McpzActivitySupport {
    /** 修改保存牧场配置线程 **/
    public static void McpzSaveThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_SAVE_MCPZ, param, "",
                XtAppConstant.WHAT_SAVE_MCPZ_THREAD, handler, context).start();
    }
    /** 修改获取牧场配置线程 **/
    public static void McpzGetThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_GET_MCPZ, param, "",
                XtAppConstant.WHAT_GET_MCPZ_THREAD, handler, context).start();
    }
}
