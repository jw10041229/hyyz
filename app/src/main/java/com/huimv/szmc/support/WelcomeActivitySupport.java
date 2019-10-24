package com.huimv.szmc.support;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;

import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.thread.HaifmServiceThread;
import com.huimv.szmc.util.HaifmCommonUtil;


public class WelcomeActivitySupport {
    /** 版本升级线程 **/
    public static void VisionCheckThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_VISION_UPGRADE, param, "", 
                XtAppConstant.WHAT_CHECK_VISION, handler, context).start();
    }
    /** 登陆验证线程 **/
    public static void LoginUserCheckThread(String param, Context context, Handler handler) {
        new HaifmServiceThread(WsCmd.HAIFM_LOGIN_USER, param, "",
                XtAppConstant.WHAT_LOGIN_USER_CHECK_THREAD, handler, context).start();
    }
    
	/**版本信息数据处理* */
	public static void doVisionDataParsing(String data,Context context) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			Spanned gengXinMsg = null;
			String url = (String) jsonObject.get("xbburl");
			gengXinMsg = Html.fromHtml((String) jsonObject.get("gxsm"));
			new HaifmCommonUtil(context, gengXinMsg, url).exitDialog();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
