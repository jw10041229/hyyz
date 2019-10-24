package com.huimv.szmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.szmc.R;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.LoginBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.LoginActivitySupport;
import com.huimv.szmc.support.WelcomeActivitySupport;
import com.huimv.szmc.util.SharePreferenceUtil;

public class WelcomeActivity extends BaseActivity implements Callback{
	public static final String TAG = WelcomeActivity.class.getSimpleName();
	private Gson gson = new Gson();
	private SharePreferenceUtil mSpUtil;
	private long exitTime = 0;
	private static int tag = -1;
	private TextView tv_version_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		tv_version_info = (TextView) findViewById(R.id.tv_version_info);
		HaifmPApplication.addActivity(this);
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		tv_version_info.setText(getString(R.string.current_bbh) + HaifmPApplication.getInstance().getLoadVersionName());
	}
	
	Runnable startAct = new Runnable() {

		@Override
		public void run() {
			if (CommonUtil.isEmpty(mSpUtil.getAccount())
					|| CommonUtil.isEmpty(mSpUtil.getPassword())) {
				tag = 1 ;
			} else {
				tag = 2;
			}
			if (tag == 1){
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
			} 
			if (tag ==2 ) {
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));
			}
			WelcomeActivity.this.finish();
		}
	};
	
	@Override
	protected void onResume() {
		Handler handler = new Handler();
		handler.postDelayed(startAct, 2000);

/*		if (!AndroidUtil.isConn(this)) {
			AndroidUtil.setNetworkMethod(this);
		} else {
			exitTime = System.currentTimeMillis();
			VersionBean version = new VersionBean();
			version.setBbh(HaifmCommonUtil.getVersionName(this));
			String param = gson.toJson(version);
			WelcomeActivitySupport.VisionCheckThread(param, this, new Handler(
				this));
		}*/
		super.onResume();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case XtAppConstant.WHAT_CHECK_VISION:
			if (msg.arg1 == WsCmd.AD_BBXX_GX) {// 有更新
				WelcomeActivitySupport.doVisionDataParsing(msg.obj.toString(),
						WelcomeActivity.this);
			}
			if (msg.arg1 == WsCmd.AD_BBXX_WGX) {// 无有更新之后验证登陆
				if (CommonUtil.isEmpty(mSpUtil.getAccount())
						|| CommonUtil.isEmpty(mSpUtil.getPassword())) {
					Handler handler = new Handler();
					handler.postDelayed(startAct, 2000);
					tag = 1;
				} else {
					LoginBean mLoginBean = new LoginBean();
					mLoginBean.setYhm(mSpUtil.getAccount().toString());
					mLoginBean.setYhmm(mSpUtil.getPassword().toString());
					mLoginBean.setYhlx(XtAppConstant.ZHUCE);
					mLoginBean.setCid(mSpUtil.getCid());
					mLoginBean.setSbid(mSpUtil.getUniqueID());
					String param = gson.toJson(mLoginBean);
					WelcomeActivitySupport.LoginUserCheckThread(param,
							WelcomeActivity.this, new Handler(
									WelcomeActivity.this));
				}
			}
			if (msg.arg1 == WsCmd.SYSTEM_ERROR) {
				ToastMsg(WelcomeActivity.this, "服务器异常");
			}// 参数错误
			break;
		case XtAppConstant.WHAT_LOGIN_USER_CHECK_THREAD:
			if (msg.arg1 == WsCmd.SUCCESS) {
				LoginActivitySupport.getLoginCheckData(msg.obj.toString(),
						WelcomeActivity.this, mSpUtil);
				if (System.currentTimeMillis() - exitTime < 1000 ) {
					Handler handler = new Handler();
					handler.postDelayed(startAct, 2000);
					tag = 2;
				} else {
					Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			} else if(msg.arg1 == WsCmd.PARAM_ERROR){
				dismissLoading();
				ToastMsg(this, "登陆失败,请检查账户密码");
				mSpUtil.setPassword("");
				startActivity(new Intent(WelcomeActivity.this,
						LoginActivity.class));
			}
			break;
		default:
			break;
		}
		return false;
	}
}
