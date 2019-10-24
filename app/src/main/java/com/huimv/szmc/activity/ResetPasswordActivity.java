package com.huimv.szmc.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.android.basic.util.PhoneUtil;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.ResetPasswordBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.ResetPasswordActivitySupport;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

public class ResetPasswordActivity extends BaseActivity implements Callback, OnClickListener {
	public static final String TAG = ResetPasswordActivity.class.getSimpleName();
	private SharePreferenceUtil mSpUtil;
	private Button yanzhengmaButton;
	private TimeCount time;
	private Button backButton;
	private TextView titleTextView;
	private Button saveButton;
	private Button registerButton;
	private EditText zhanghuEditText;
	private EditText shoujihaoEditText;
	private EditText yanzhengmaEditText;
	private TextView resetResultTextview;
	private Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		initView();
		initData();
	}

	private void initData() {
		HaifmPApplication.addActivity(this);
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		time = new TimeCount(60000, 1000);
		titleTextView.setText(getString(R.string.findP_password_title));
		zhanghuEditText.setText(mSpUtil.getAccount());
		titleTextView.setText(getString(R.string.reset_password));
		registerButton.setText(getString(R.string.reset_password));
	}

	private void initView() {
		yanzhengmaButton = (Button) findViewById(R.id.yanzhengmaButton);
		backButton = (Button) findViewById(R.id.button_back);
		titleTextView = (TextView) findViewById(R.id.textview_title);
		resetResultTextview = (TextView) findViewById(R.id.resetResultTextview);
		zhanghuEditText = (EditText) findViewById(R.id.zhanghuEditText);
		shoujihaoEditText = (EditText) findViewById(R.id.shoujihaoEditText);
		shoujihaoEditText.setInputType(InputType.TYPE_CLASS_PHONE);
		yanzhengmaEditText = (EditText) findViewById(R.id.yanzhengmaEditText);
		saveButton = (Button) findViewById(R.id.button_save);
		registerButton = (Button) findViewById(R.id.findPButton);
		saveButton.setVisibility(View.INVISIBLE);
		yanzhengmaButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		dismissLoading();
		switch (msg.what) {
		case XtAppConstant.WHAT_RESET_PASSWORD_THREAD:
			if (msg.arg1 == WsCmd.SUCCESS) {
				resetResultTextview.setVisibility(View.VISIBLE);
				resetResultTextview.setText("密码已重置为123456,请及时修改密码");
				mSpUtil.setPassword("123456");
			}
			if (msg.arg1 == WsCmd.PARAM_ERROR) { // 手机与账户不匹配
				resetResultTextview.setVisibility(View.VISIBLE);
				resetResultTextview.setText("手机与账户不匹配");
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		resetResultTextview.setVisibility(View.GONE);
		switch (v.getId()) {
		case R.id.yanzhengmaButton:
			if (!PhoneUtil.isMobileNO(shoujihaoEditText.getText().toString().trim())) {
				ToastMsg(ResetPasswordActivity.this, getString(R.string.register_shoujihao_right));
				return;
			}
			time.start();// 开始计时
			break;
		case R.id.button_back:
			finish();
			break;
		case R.id.findPButton:
			if (CommonUtil.isEmpty(zhanghuEditText.getText().toString().trim())) {
				zhanghuEditText.setHint(getString(R.string.register_zhuanghu_notNull));
				zhanghuEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (CommonUtil.isEmpty(shoujihaoEditText.getText().toString().trim())) {
				shoujihaoEditText.setText("");
				shoujihaoEditText.setHint(getString(R.string.shoujihao_notNull));
				shoujihaoEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
/*			if (CommonUtil.isEmpty(yanzhengmaEditText.getText().toString().trim())) {
				yanzhengmaEditText.setText("");
				yanzhengmaEditText.setHint(getString(R.string.register_yanzhengma_notNull));
				yanzhengmaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}*/
			if (!PhoneUtil.isMobileNO(shoujihaoEditText.getText().toString().trim())) {
				ToastMsg(ResetPasswordActivity.this, getString(R.string.register_shoujihao_right));
				return;
			}
/*			ToolSMS.submitVerificationCode(shoujihaoEditText.getText().toString().trim(),
					yanzhengmaEditText.getText().toString().trim(), new ToolSMS.IValidateSMSCode() {

						@Override
						public void onSucced() {
							// 修改线程

						}

						@Override
						public void onFailed(Throwable e) {
							dismissLoading();
							ToastMsg(ResetPasswordActivity.this, "验证码不正确");
						}
					});*/
			getData();
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
	private void getData () {
		showLoading();
		ResetPasswordBean mResetPasswordBean = new ResetPasswordBean();
		mResetPasswordBean.setYhm(zhanghuEditText.getText().toString().trim());
		mResetPasswordBean.setLxdh(shoujihaoEditText.getText().toString().trim());
		mResetPasswordBean.setYzm(yanzhengmaEditText.getText().toString().trim());
		String param = gson.toJson(mResetPasswordBean);
		ResetPasswordActivitySupport.ResetPasswordThread(param, ResetPasswordActivity.this,
				new Handler(ResetPasswordActivity.this));
	}
}
