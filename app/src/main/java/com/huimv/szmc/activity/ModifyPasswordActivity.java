package com.huimv.szmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.android.basic.util.PhoneUtil;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.ModifyPasswordBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.ModifyPasswordActivitySupport;
import com.huimv.szmc.util.MD5Util;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

public class ModifyPasswordActivity extends BaseActivity implements Callback, OnClickListener {
	public static final String TAG = ModifyPasswordActivity.class.getSimpleName();
	private SharePreferenceUtil mSpUtil;
	private Button yanzhengmaButton;
	private TimeCount time;
	private Button backButton;
	private TextView titleTextView;
	private Button saveButton;
	private Button modifyPButton;
	private EditText zhanghuEditText;
	private EditText shoujihaoEditText;
	private EditText yanzhengmaEditText;
	private EditText newmimaEditText;
	private EditText mimaConfimEditText;
	private TextView resetResultTextview;
	private RelativeLayout register_mimaRL;
	private RelativeLayout register_newmimaRL;
	private RelativeLayout register_mimaConfirmRL;
	private TextView findP_mimaText;
	private Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_password);
		initView();
		initData();
	}

	private void initData() {
		HaifmPApplication.addActivity(this);
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		time = new TimeCount(60000, 1000);
		titleTextView.setText(getString(R.string.findP_password_title));
		zhanghuEditText.setText(mSpUtil.getAccount());
		titleTextView.setText(getString(R.string.modify_password_title));
		modifyPButton.setText(getString(R.string.modify_password_title));
	}

	private void initView() {
		yanzhengmaButton = (Button) findViewById(R.id.yanzhengmaButton);
		backButton = (Button) findViewById(R.id.button_back);
		titleTextView = (TextView) findViewById(R.id.textview_title);
		resetResultTextview = (TextView) findViewById(R.id.resetResultTextview);
		zhanghuEditText = (EditText) findViewById(R.id.zhanghuEditText);
		shoujihaoEditText = (EditText) findViewById(R.id.shoujihaoEditText);
		yanzhengmaEditText = (EditText) findViewById(R.id.yanzhengmaEditText);
		newmimaEditText = (EditText) findViewById(R.id.newmimaEditText);
		mimaConfimEditText = (EditText) findViewById(R.id.mimaConfimEditText);
		newmimaEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		Editable etable = newmimaEditText.getText();
		Selection.setSelection(etable, etable.length());
		mimaConfimEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		Editable etable1 = mimaConfimEditText.getText();
		Selection.setSelection(etable1, etable.length());

		saveButton = (Button) findViewById(R.id.button_save);
		modifyPButton = (Button) findViewById(R.id.modifyPButton);
		findP_mimaText = (TextView) findViewById(R.id.findP_mimaText);
		findP_mimaText.setText(getString(R.string.register_oldpassword));
		saveButton.setVisibility(View.INVISIBLE);
		zhanghuEditText.setEnabled(false);
		register_mimaRL = (RelativeLayout) findViewById(R.id.register_mimaRL);
		register_newmimaRL = (RelativeLayout) findViewById(R.id.register_newmimaRL);
		register_mimaConfirmRL = (RelativeLayout) findViewById(R.id.register_mimaConfirmRL);
		register_mimaRL.setVisibility(View.GONE);
		register_newmimaRL.setVisibility(View.VISIBLE);
		register_mimaConfirmRL.setVisibility(View.VISIBLE);
		yanzhengmaButton.setOnClickListener(this);
		modifyPButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		dismissLoading();
		switch (msg.what) {
		case XtAppConstant.WHAT_MODIFY_PASSWORD_THREAD:
			if (msg.arg1 == WsCmd.SUCCESS) {
				mSpUtil.setPassword(newmimaEditText.getText().toString());
				ToastMsg(ModifyPasswordActivity.this, "密码修改成功,请重新登陆");
				startActivity(new Intent(this, LoginActivity.class));
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
			if (!PhoneUtil.isMobileNO(shoujihaoEditText.getText().toString())) {
				ToastMsg(ModifyPasswordActivity.this, getString(R.string.register_shoujihao_right));
				return;
			}
			time.start();// 开始计时
			break;
		case R.id.button_back:
			finish();
			break;
		case R.id.modifyPButton:
			if (CommonUtil.isEmpty(shoujihaoEditText.getText().toString())) {
				shoujihaoEditText.setHint(getString(R.string.shoujihao_notNull));
				shoujihaoEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (!PhoneUtil.isMobileNO(shoujihaoEditText.getText().toString())) {
				ToastMsg(ModifyPasswordActivity.this, getString(R.string.register_shoujihao_right));
				return;
			}
			/*
			if (CommonUtil.isEmpty(yanzhengmaEditText.getText().toString())) {
				yanzhengmaEditText.setHint(getString(R.string.register_yanzhengma_notNull));
				yanzhengmaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}*/
			if (newmimaEditText.getText().toString().length() < 6) {
				newmimaEditText.setText("");
				mimaConfimEditText.setText("");
				newmimaEditText.setHint(getString(R.string.register_mima_6));
				newmimaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (CommonUtil.isEmpty(newmimaEditText.getText().toString())) {
				newmimaEditText.setHint(getString(R.string.register_newPassword_notNull));
				newmimaEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (CommonUtil.isEmpty(mimaConfimEditText.getText().toString())) {
				mimaConfimEditText.setHint(getString(R.string.register_confimPassword_notNull));
				mimaConfimEditText.setHintTextColor(getResources().getColor(R.color.error_red));
				return;
			}
			if (!mimaConfimEditText.getText().toString().equals(newmimaEditText.getText().toString())) {
				ToastMsg(ModifyPasswordActivity.this, getString(R.string.register_mima_notFill));
				return;
			}
/*			ToolSMS.submitVerificationCode(shoujihaoEditText.getText().toString(),
					yanzhengmaEditText.getText().toString(), new ToolSMS.IValidateSMSCode() {

						@Override
						public void onSucced() {
							// 修改线程
							//showLoading();
							//getData();
						}

						@Override
						public void onFailed(Throwable e) {
							dismissLoading();
							ToastMsg(ModifyPasswordActivity.this, "验证码不正确");
						}
					});*/
			showLoading();
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
		ModifyPasswordBean mModifyPasswordBean = new ModifyPasswordBean();
		mModifyPasswordBean.setYhm(zhanghuEditText.getText().toString().trim());
		mModifyPasswordBean.setLxdh(shoujihaoEditText.getText().toString().trim());
		mModifyPasswordBean.setYzm(yanzhengmaEditText.getText().toString().trim());
		mModifyPasswordBean.setXyhmm(MD5Util.crypt(newmimaEditText.getText().toString().trim()));
		mModifyPasswordBean.setQrxmm(mimaConfimEditText.getText().toString().trim());
		String param = gson.toJson(mModifyPasswordBean);
		ModifyPasswordActivitySupport.ModifyPasswordThread(param, ModifyPasswordActivity.this,
				new Handler(ModifyPasswordActivity.this));
	}
}
