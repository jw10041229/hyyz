package com.huimv.szmc.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
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
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.ModifyNichengBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.ModifyNichengActivitySupport;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

public class NichengModifyActivity extends BaseActivity implements OnClickListener, Callback{
    private Button button_back;
    private TextView textview_title;
    private Button button_save;
    private ImageView deleteImageView;
    private EditText nichengEditText;
    private SharePreferenceUtil mSpUtil;
    private Gson gson = new Gson();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_right_item_nicheng);
		init();
		nichengEditText.setText(mSpUtil.getNicheng());
	}
	
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteImageView:
                nichengEditText.setText("");
                break;
            case R.id.button_back:
                finish();
                break;
            case R.id.button_save:
                if (!AndroidUtil.isConn(NichengModifyActivity.this)) {
                    AndroidUtil.setNetworkMethod(NichengModifyActivity.this);
                    return;
                }
                if (mSpUtil.getYoukeTag().equals(XtAppConstant.YOUKE)) {
                    ToastMsg(NichengModifyActivity.this, getString(R.string.visit_not_modify));
                    return;
                }
                if (CommonUtil.isEmpty(nichengEditText.getText().toString())) {
                    ToastMsg(NichengModifyActivity.this, getString(R.string.nicheng_notNull));
                    return;
                }
                if (nichengEditText.getText().toString().length() > XtAppConstant.NNCD) {
                    ToastMsg(NichengModifyActivity.this, getString(R.string.register_nicheng_length) + XtAppConstant.NNCD);
                    return;
                }
                showLoading();
                ModifyNichengBean mModifyNichengBean = new ModifyNichengBean();
                mModifyNichengBean.setYhnc(nichengEditText.getText().toString());
                mModifyNichengBean.setYhid(mSpUtil.getUserID());
                String param = gson.toJson(mModifyNichengBean);
                ModifyNichengActivitySupport.ModifyNichengThread(param, NichengModifyActivity.this, new Handler(NichengModifyActivity.this));
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void init() {
        button_back = (Button) findViewById(R.id.button_back);
        button_save = (Button) findViewById(R.id.button_save);
        textview_title = (TextView) findViewById(R.id.textview_title);
        deleteImageView = (ImageView) findViewById(R.id.deleteImageView);
        nichengEditText = (EditText) findViewById(R.id.nichengEditText);
        textview_title.setText(getString(R.string.right_title_nicheng));
        HaifmPApplication.addActivity(this);
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        deleteImageView.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case XtAppConstant.WHAT_MODIFY_NICHENG_THREAD:
			dismissLoading();
			if (msg.arg1 == WsCmd.SUCCESS) { //修改成功
				mSpUtil.setNicheng(nichengEditText.getText().toString());
				nichengEditText.setText(mSpUtil.getNicheng());
				ToastMsg(NichengModifyActivity.this, "昵称修改成功");
			}
		if (msg.arg1 == WsCmd.PARAM_ERROR) { //修改失败
			ToastMsg(NichengModifyActivity.this, "此昵称已存在");
			}
			break;

		default:
			break;
		}
		return false;
	}
}
