package com.huimv.szmc.support;

import android.content.Context;
import android.os.Handler;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;
import com.huimv.szmc.util.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivitySupport {
    /** 登陆验证线程 **/
    public static void LoginUserCheckThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_LOGIN_USER, param, "",
                XtAppConstant.WHAT_LOGIN_USER_CHECK_THREAD, handler, context).start();
    }
    /** 访客登陆验证线程 **/
    public static void LoginGuestCheckThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_LOGIN_GUEST, param, "", 
                XtAppConstant.WHAT_LOGIN_GUEST_CHECK_THREAD, handler, context).start();
    }
    
    /**登陆返回数据处理* */
    public static void getLoginCheckData(String data,Context context, SharePreferenceUtil mSpUtil) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String yhid = jsonObject.get("yhid").toString();
            String yhnc = jsonObject.get("yhnc").toString();
            String lxdh = jsonObject.get("lxdh").toString();
            String uuid = jsonObject.get("uuid").toString();
            mSpUtil.setUserID(yhid);
            mSpUtil.setNicheng(yhnc);
            mSpUtil.setShoujihao(lxdh);
            mSpUtil.setUUId(uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
