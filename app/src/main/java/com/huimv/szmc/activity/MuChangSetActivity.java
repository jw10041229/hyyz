package com.huimv.szmc.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.AndroidUtil;
import com.huimv.szmc.R;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.McPzBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.McpzActivitySupport;
import com.huimv.szmc.util.SharePreferenceUtil;
public class MuChangSetActivity extends BaseActivity implements OnClickListener, Callback{
    private Button button_back;
    private TextView textview_title;
    private Button button_save;
    private Button btn_refresh;
    private SharePreferenceUtil mSpUtil;
    private Gson gson = new Gson();
    private EditText et_zzrlzdrl;//种猪入栏最大日龄
    private EditText et_scpzzxrl;//首次配种最小日龄

    private EditText et_rssbfqzdsj;//妊娠失败反情最大日期;
    private EditText et_dncqpzzdrl;//断奶重新配种最大日龄

    private EditText et_sccjzxrl;//首次采精最小日龄

    private EditText et_pzhzcrs;//配种后最长妊娠时间
    private EditText et_pzhzdrs;//配种后最短妊娠时间


    private EditText et_pzhfmzcrq;//配种后分娩最长日期
    private EditText et_pzhfmzdrq;//配种后分娩最短日期

    private EditText et_fmhzddnrq;//分娩后断奶最大日龄
    private EditText et_fmhzxdnrq;//分娩后断奶最小日龄

    private EditText et_ttgzzcrl;//淘汰公猪最大日龄
    private EditText et_ttmzzcrl;//淘汰母猪最小日龄

    private EditText et_zddnts;//最大断奶头数
    private EditText et_zxdnts;//最小断奶头数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_right_item_mc_set);
		init();
	}
	
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_save:
                if (!AndroidUtil.isConn(MuChangSetActivity.this)) {
                    AndroidUtil.setNetworkMethod(MuChangSetActivity.this);
                    return;
                }
                if (mSpUtil.getYoukeTag().equals(XtAppConstant.YOUKE)) {
                    ToastMsg(MuChangSetActivity.this, getString(R.string.visit_not_modify));
                    return;
                }
                showLoading("正在保存...");
                McPzBean mcPzBean = new McPzBean();
                mcPzBean.setUuid(mSpUtil.getUUId());
                mcPzBean.setZzrlzdrl(et_zzrlzdrl.getText().toString().trim());
                mcPzBean.setScpzzxrl(et_scpzzxrl.getText().toString().trim());
                mcPzBean.setRssbfqzdsj(et_rssbfqzdsj.getText().toString().trim());
                mcPzBean.setDncqpzzdrl(et_dncqpzzdrl.getText().toString().trim());
                mcPzBean.setSccjzxrl(et_sccjzxrl.getText().toString().trim());

                mcPzBean.setPzhzdfmrq(et_pzhfmzdrq.getText().toString().trim());
                mcPzBean.setPzhzcfmrq(et_pzhfmzcrq.getText().toString().trim());

                mcPzBean.setPzhzcrsrq(et_pzhzcrs.getText().toString().trim());
                mcPzBean.setPzhzdrsrq(et_pzhzdrs.getText().toString().trim());

                mcPzBean.setFmhzddnrq(et_fmhzxdnrq.getText().toString().trim());
                mcPzBean.setFmhzcdnrq(et_fmhzddnrq.getText().toString().trim());

                mcPzBean.setTtgzzcrl(et_ttgzzcrl.getText().toString().trim());
                mcPzBean.setTtmzzcrl(et_ttmzzcrl.getText().toString().trim());

                mcPzBean.setDnzdts(et_zddnts.getText().toString().trim());
                mcPzBean.setDnzxts(et_zxdnts.getText().toString().trim());
                String param = gson.toJson(mcPzBean);
                McpzActivitySupport.McpzSaveThread(param, MuChangSetActivity.this, new Handler(MuChangSetActivity.this));
                break;
            case R.id.button_refresh:
                showLoading("正在刷新...");
                McpzActivitySupport.McpzGetThread("", MuChangSetActivity.this, new Handler(MuChangSetActivity.this));
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    private void init() {
        btn_refresh = (Button) findViewById(R.id.button_refresh);
        et_zzrlzdrl = (EditText) findViewById(R.id.et_zzrlzdrl);
        et_scpzzxrl = (EditText) findViewById(R.id.et_scpzzxrl);
        et_rssbfqzdsj = (EditText) findViewById(R.id.et_rssbfqzdsj);
        et_dncqpzzdrl = (EditText) findViewById(R.id.et_dncqpzzdrl);
        et_sccjzxrl = (EditText) findViewById(R.id.et_sccjzxrl);
        et_pzhzcrs = (EditText) findViewById(R.id.et_pzhzcrs);
        et_pzhzdrs = (EditText) findViewById(R.id.et_pzhzdrs);
        et_pzhfmzcrq = (EditText) findViewById(R.id.et_pzhfmzcrq);
        et_pzhfmzdrq = (EditText) findViewById(R.id.et_pzhfmzdrq);
        et_fmhzddnrq = (EditText) findViewById(R.id.et_fmhzddnrq);
        et_fmhzxdnrq = (EditText) findViewById(R.id.et_fmhzxdnrq);
        et_ttgzzcrl = (EditText) findViewById(R.id.et_ttgzzcrl);
        et_ttmzzcrl = (EditText) findViewById(R.id.et_ttmzzcrl);
        et_zddnts = (EditText) findViewById(R.id.et_zddnts);
        et_zxdnts = (EditText) findViewById(R.id.et_zxdnts);

        button_back = (Button) findViewById(R.id.button_back);
        button_save = (Button) findViewById(R.id.button_save);
        textview_title = (TextView) findViewById(R.id.textview_title);
        textview_title.setText(getString(R.string.right_title_muchangshezhi));
        btn_refresh.setVisibility(View.VISIBLE);
        HaifmPApplication.addActivity(this);
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        button_back.setOnClickListener(this);
        button_save.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
    }

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case XtAppConstant.WHAT_SAVE_MCPZ_THREAD:
			dismissLoading();
			if (msg.arg1 == WsCmd.SUCCESS) { //修改成功
				ToastMsg(MuChangSetActivity.this, "保存成功");
			}
		if (msg.arg1 == WsCmd.PARAM_ERROR) { //修改失败
			ToastMsg(MuChangSetActivity.this, "保存失败");
			}
			break;
            case XtAppConstant.WHAT_GET_MCPZ_THREAD:
                dismissLoading();
                break;
		default:
			break;
		}
		return false;
	}
}
