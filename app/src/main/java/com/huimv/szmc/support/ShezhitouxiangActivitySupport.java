package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;


public class ShezhitouxiangActivitySupport {
    /** 上传头像线程 **/
    public static void UploadThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_YHTX_UPLOAD, param, "",
                XtAppConstant.WHAT_UPLOAD_TOUXIANG_THREAD, handler, context).start();
    }
}
