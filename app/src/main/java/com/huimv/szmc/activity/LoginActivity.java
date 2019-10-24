package com.huimv.szmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.AndroidUtil;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.szmc.R;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.LoginBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.LoginActivitySupport;
import com.huimv.szmc.util.MD5Util;
import com.huimv.szmc.util.SharePreferenceUtil;

public class LoginActivity extends BaseActivity implements Callback,
		OnClickListener {
	//private static final String TAG = LoginActivity.class.getSimpleName();
	private EditText accountEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private Button visitLoginButton;
	private TextView registerTextView;
	private TextView wjpasswordTextView;
	private ImageView topImageView;
	private ImageView passwordImageView;
	private SharePreferenceUtil mSpUtil;
	private Gson gson = new Gson();
	private TextView banBenXinXin;
	private HaifmPApplication mHaifmApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		mHaifmApplication = (HaifmPApplication) this.getApplicationContext();
		HaifmPApplication.addActivity(this);
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		loginButton = (Button) findViewById(R.id.loginButton);
		visitLoginButton = (Button) findViewById(R.id.visitLoginButton);
		accountEditText = (EditText) findViewById(R.id.accountEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		registerTextView = (TextView) findViewById(R.id.registerTextView);
		wjpasswordTextView = (TextView) findViewById(R.id.wjpasswordTextView);
		topImageView = (ImageView) findViewById(R.id.topImageView);
		passwordImageView = (ImageView) findViewById(R.id.passwordImageView);
		accountEditText.setText(mSpUtil.getAccount());
		passwordEditText.setText(mSpUtil.getPassword());
		loginButton.setOnClickListener(this);
		visitLoginButton.setOnClickListener(this);
		registerTextView.setOnClickListener(this);
		wjpasswordTextView.setOnClickListener(this);
		passwordImageView.setOnClickListener(this);
		banBenXinXin = (TextView) this.findViewById(R.id.banbenxinxi);
		banBenXinXin.setText(getString(R.string.current_bbh) + mHaifmApplication.getLoadVersionName());
	}

	@Override
	protected void onResume() {
		// accountEditText.setText(mSpUtil.getAccount());
		// passwordEditText.setText(mSpUtil.getPassword());
		super.onResume();
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == XtAppConstant.WHAT_LOGIN_USER_CHECK_THREAD) {
			if (msg.arg1 == WsCmd.SUCCESS) {
				ToastMsg(LoginActivity.this, "登陆成功");
				LoginActivitySupport.getLoginCheckData(msg.obj.toString(),
						LoginActivity.this, mSpUtil);
				mSpUtil.setYoukeTag(XtAppConstant.ZHUCE);
				mSpUtil.setAccount(accountEditText.getText().toString());
				mSpUtil.setPassword(MD5Util.crypt(passwordEditText.getText()
						.toString()));
				mSpUtil.setHeadPicUrl(ShezhitouxiangActivity.path
						+ ShezhitouxiangActivity.HAIFMPIC + mSpUtil.getUserID()
						+ ".jpg");
				mSpUtil.setIsLogined("1");
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				intent.putExtra("xxid", "-1");
				startActivity(intent);
				dismissLoading();
				finish();
			}
			if (msg.arg1 == WsCmd.PARAM_ERROR) {
				dismissLoading();
				ToastMsg(LoginActivity.this, "登陆失败,请检查账户密码");
			}
		}
		if (msg.what == XtAppConstant.WHAT_LOGIN_GUEST_CHECK_THREAD) {
			if (msg.arg1 == WsCmd.SUCCESS) {
				mSpUtil.setNicheng("访客");
				mSpUtil.setPassword("");
				mSpUtil.setYoukeTag(XtAppConstant.YOUKE);
				mSpUtil.setShoujihao("");
				mSpUtil.setHeadPicUrl("");
				mSpUtil.setIsLogined("1");
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				intent.putExtra("xxid", "-1");
				startActivity(intent);
				dismissLoading();
				finish();
			}
			if (msg.arg1 == WsCmd.PARAM_ERROR) {
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			if (!AndroidUtil.isConn(LoginActivity.this)) {
				AndroidUtil.setNetworkMethod(LoginActivity.this);
				return;
			}
			if (CommonUtil.isEmpty(accountEditText.getText().toString())) {
				ToastMsg(LoginActivity.this,getString(R.string.leaf_account_notNull));
				return;
			}
			if (CommonUtil.isEmpty(passwordEditText.getText().toString())) {
				ToastMsg(LoginActivity.this,getString(R.string.leaf_password_notNull));
				return;
			}
			showLoading();
			LoginBean mLoginBean = new LoginBean();
			mLoginBean.setYhm(accountEditText.getText().toString());
			mLoginBean.setYhmm(MD5Util.crypt(passwordEditText.getText()
					.toString()));
			mLoginBean.setYhlx(XtAppConstant.ZHUCE);
			mLoginBean.setCid(mSpUtil.getCid());
			mLoginBean.setSbid(mSpUtil.getUniqueID());
			String param = gson.toJson(mLoginBean);
			LoginActivitySupport.LoginUserCheckThread(param,
					LoginActivity.this, new Handler(this));
			break;
		case R.id.visitLoginButton:
			if (!AndroidUtil.isConn(LoginActivity.this)) {
				AndroidUtil.setNetworkMethod(LoginActivity.this);
				return;
			}
			showLoading();
			LoginBean mLoginVisitBean = new LoginBean();
			mLoginVisitBean.setYhm(accountEditText.getText().toString());
			mLoginVisitBean.setYhmm(MD5Util.crypt(passwordEditText.getText()
					.toString()));
			mLoginVisitBean.setYhlx(XtAppConstant.YOUKE);
			mLoginVisitBean.setCid(mSpUtil.getCid());
			String paramVisit = gson.toJson(mLoginVisitBean);
			LoginActivitySupport.LoginGuestCheckThread(paramVisit,
					LoginActivity.this, new Handler(this));
			break;
		case R.id.registerTextView:
			Intent intentRegister = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intentRegister);
			//ToastMsg(this, "此功能当前不可用");
			break;
		case R.id.wjpasswordTextView:
		Intent intentFindPassword = new Intent(LoginActivity.this,
					ResetPasswordActivity.class);
			startActivity(intentFindPassword);
			//ToastMsg(this, "此功能当前不可用");
			break;
		case R.id.passwordImageView:
			if (passwordEditText.getInputType() == InputType.// 显示密码
			TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
				passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				passwordImageView.setImageDrawable(getResources().getDrawable(
						R.drawable.login_key_hightlighted));
			} else {// 隐藏密码
				passwordEditText
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				passwordImageView.setImageDrawable(getResources().getDrawable(
						R.drawable.login_key));
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}
}
