package com.huimv.szmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.android.basic.util.PhoneUtil;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.RegisterBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.RegisterActivitySupport;
import com.huimv.szmc.util.MD5Util;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

public class RegisterActivity extends BaseActivity implements Callback, OnClickListener {
	public static final String TAG = RegisterActivity.class.getSimpleName();
	private SharePreferenceUtil mSpUtil;
	private Button yanzhengmaButton;
	private TimeCount time;
	private Button backButton;
	private TextView titleTextView;
	private Button saveButton;
	private Button registerButton;
	private EditText zhanghuEditText;
	private EditText nichengEditText;
	private EditText shoujihaoEditText;
	private EditText yanzhengmaEditText;
	private EditText mimaEditText;
	private EditText mimaConfimEditText;
	private TextView shoujihaoExisitTextView;
	private TextView nichengExisitTextView;
	private TextView zhanghuExisitTextView;
	private Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initData();
	}

	private void initData() {
		HaifmPApplication.addActivity(this);
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		time = new TimeCount(60000, 1000);
		titleTextView.setText(getString(R.string.register_zhuce_new));
	}

	private void initView() {
		yanzhengmaButton = (Button) findViewById(R.id.yanzhengmaButton);
		backButton = (Button) findViewById(R.id.button_back);
		titleTextView = (TextView) findViewById(R.id.textview_title);
		zhanghuExisitTextView = (TextView) findViewById(R.id.zhanghuExisitTextView);
		nichengExisitTextView = (TextView) findViewById(R.id.nichengExisitTextView);
		shoujihaoExisitTextView = (TextView) findViewById(R.id.shoujihaoExisitTextView);
		zhanghuEditText = (EditText) findViewById(R.id.zhanghuEditText);
		nichengEditText = (EditText) findViewById(R.id.nichengEditText);
		shoujihaoEditText = (EditText) findViewById(R.id.shoujihaoEditText);
		yanzhengmaEditText = (EditText) findViewById(R.id.yanzhengmaEditText);
		mimaEditText = (EditText) findViewById(R.id.mimaEditText);
		mimaConfimEditText = (EditText) findViewById(R.id.mimaConfimEditText);
		mimaEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mimaConfimEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		saveButton = (Button) findViewById(R.id.button_save);
		registerButton = (Button) findViewById(R.id.findPButton);
		saveButton.setVisibility(View.INVISIBLE);
		yanzhengmaButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		zhanghuEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					zhanghuExisitTextView.setVisibility(View.INVISIBLE);
				}
			}
		});
		nichengEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					nichengExisitTextView.setVisibility(View.INVISIBLE);
				}
			}
		});
		shoujihaoEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					shoujihaoExisitTextView.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	@Override
	public boolean handleMessage(Message msg) {
		dismissLoading();
		switch (msg.what) {
		case XtAppConstant.WHAT_REGISTER_THREAD:
			if (msg.arg1 == WsCmd.SUCCESS) { // 全部验证通过
				mSpUtil.setAccount(zhanghuEditText.getText().toString());
				mSpUtil.setNicheng(nichengEditText.getText().toString());
				mSpUtil.setPassword(MD5Util.crypt(mimaEditText.getText().toString()));
				mSpUtil.setShoujihao(shoujihaoEditText.getText().toString());
				String yhid = msg.obj.toString();
				mSpUtil.setUserID(yhid);
				String fileName = ShezhitouxiangActivity.path + ShezhitouxiangActivity.HAIFMPIC + mSpUtil.getUserID()
						+ ".jpg";// 图片名字
				mSpUtil.setHeadPicUrl(fileName);
				mSpUtil.setYoukeTag(XtAppConstant.ZHUCE);
				// TODO haifm 注册成功之后的userID 有待测试
				ToastMsg(RegisterActivity.this, "注册成功");
				startActivity(new Intent(RegisterActivity.this, MainActivity.class));
				// 释放监听器
				finish();
			} else if (msg.arg1 == WsCmd.PARAM_ERROR) {
				String result = msg.obj.toString();
				if (result.length() == 3) {
					if (result.substring(0, 1).endsWith("1")) {
						zhanghuExisitTextView.setVisibility(View.VISIBLE);
					}
					if (result.substring(1, 2).endsWith("1")) {
						nichengExisitTextView.setVisibility(View.VISIBLE);
					}
					if (result.substring(2, 3).endsWith("1")) {
						shoujihaoExisitTextView.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yanzhengmaButton:
			if (!PhoneUtil.isMobileNO(shoujihaoEditText.getText().toString())) {
				ToastMsg(RegisterActivity.this, getString(R.string.register_shoujihao_right));
				return;
			}
			time.start();// 开始计时
			break;
		case R.id.button_back:
			finish();
			break;
		case R.id.findPButton:
			if (CommonUtil.isEmpty(zhanghuEditText.getText().toString())) {
				zhanghuEditText.setHint(getString(R.string.register_zhuanghu_notNull));
				zhanghuEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (CommonUtil.isEmpty(nichengEditText.getText().toString())) {
				nichengEditText.setHint(getString(R.string.nicheng_notNull));
				nichengEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			} else if (nichengEditText.getText().toString().length() > XtAppConstant.NNCD) {
				ToastMsg(RegisterActivity.this, getString(R.string.register_nicheng_length) + XtAppConstant.NNCD);
				return;
			}
			if (CommonUtil.isEmpty(shoujihaoEditText.getText().toString())) {
				shoujihaoEditText.setHint(getString(R.string.shoujihao_notNull));
				shoujihaoEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
/*			if (CommonUtil.isEmpty(yanzhengmaEditText.getText().toString())) {
				yanzhengmaEditText.setHint(getString(R.string.register_yanzhengma_notNull));
				yanzhengmaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}*/
			if (CommonUtil.isEmpty(mimaEditText.getText().toString())) {
				mimaEditText.setHint(getString(R.string.leaf_password_notNull));
				mimaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (mimaEditText.getText().toString().length() < 6) {
				ToastMsg(RegisterActivity.this, getString(R.string.register_mima_6));
				return;
			}
			if (CommonUtil.isEmpty(mimaConfimEditText.getText().toString())) {
				mimaConfimEditText.setHint(getString(R.string.register_confimPassword_notNull));
				mimaConfimEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (!mimaConfimEditText.getText().toString().equals(mimaEditText.getText().toString())) {
				ToastMsg(RegisterActivity.this, getString(R.string.register_mima_notFill));
				return;
			}
			showLoading();
			// 注册线程
			RegisterActivitySupport.RegisterThread(getRegisterData(), RegisterActivity.this,
					new Handler(RegisterActivity.this));
/*			ToolSMS.submitVerificationCode(shoujihaoEditText.getText().toString(),
					yanzhengmaEditText.getText().toString(), new ToolSMS.IValidateSMSCode() {

						@Override
						public void onSucced() {

						}

						@Override
						public void onFailed(Throwable e) {
							dismissLoading();
							ToastMsg(RegisterActivity.this, "验证码不正确");
						}
					});*/
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onDestroy() {
		time.cancel();
		super.onDestroy();
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			yanzhengmaButton.setClickable(false);
			yanzhengmaButton.setText(millisUntilFinished / 1000 + "秒后重发");
		}

		@Override
		public void onFinish() {
			yanzhengmaButton.setClickable(true);
			yanzhengmaButton.setText("重新发送");
		}
	}

	public String getRegisterData() {
		RegisterBean mRegisterBean = new RegisterBean();
		mRegisterBean.setYhmmAgain(mimaConfimEditText.getText().toString());
		mRegisterBean.setYhm(zhanghuEditText.getText().toString());
		mRegisterBean.setYhnc(nichengEditText.getText().toString());
		mRegisterBean.setLxdh(shoujihaoEditText.getText().toString());
		mRegisterBean.setYhmm(MD5Util.crypt(mimaEditText.getText().toString()));
		mRegisterBean.setYzm(yanzhengmaEditText.getText().toString());
		return gson.toJson(mRegisterBean);
	}
}
