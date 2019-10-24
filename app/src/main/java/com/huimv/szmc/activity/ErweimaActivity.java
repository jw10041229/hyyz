package com.huimv.szmc.activity;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;



public class ErweimaActivity extends BaseActivity implements OnClickListener, Callback{
    private Button button_back;
    private TextView textview_title;
    private Button button_save;
    private SharePreferenceUtil mSpUtil;
    private ImageView iv_yzzs_ewm;
    private ImageView iv_haifmp_ewm;
    private Gson gson = new Gson();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_right_item_erweima);
		init();
        initData();
	}

    private void initData() {
        Glide.with(this).load(XtAppConstant.yzzsEwmUrl).into(iv_yzzs_ewm);
        Glide.with(this).load(XtAppConstant.haifmPEwmUrl).into(iv_haifmp_ewm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void init() {
        iv_yzzs_ewm = (ImageView) findViewById(R.id.iv_yzzs_ewm);
        iv_haifmp_ewm = (ImageView) findViewById(R.id.iv_haifmp_ewm);
        button_back = (Button) findViewById(R.id.button_back);
        button_save = (Button) findViewById(R.id.button_save);
        textview_title = (TextView) findViewById(R.id.textview_title);
        textview_title.setText(getString(R.string.app_erweima));
        button_save.setVisibility(View.INVISIBLE);
        HaifmPApplication.addActivity(this);
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        button_back.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }

	@Override
	public boolean handleMessage(Message msg) {
        return false;
    }
}
