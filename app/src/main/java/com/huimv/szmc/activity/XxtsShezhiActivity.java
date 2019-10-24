package com.huimv.szmc.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.widget.switchButton.SwitchButton;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

/**
 * 消息推送设置
 * @author jiangwei
 *
 */
public class XxtsShezhiActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{
	LinearLayout llayoutxxtz, llayouttz, llayoutsl, llayoutlrb, llayoutfw;
	RelativeLayout tzlayout, lrblayout, fwlayout, sllayout;
	RelativeLayout wdlayout;
	SwitchButton xxxkgbtn;//总开关
	SwitchButton wdkgbtn;//温度开关
	SwitchButton wlkgbtn;//网络开关
	SwitchButton sjkgbtn;//事件开关
	//SwitchButton gzkgbtn;//故障开关
	SwitchButton sbkgbtn;//设备开关
	SwitchButton rslbhkgbtn;//日饲料变化开关
	SwitchButton rslkgbtn;//日饲料开关
	SwitchButton qclrbbhkgbtn;//全程料肉比变化
	SwitchButton qclrbkgbtn;//全程料肉比
	
	SwitchButton qtrpjfwbhkgbtn;//群体日平均访问变化
	SwitchButton qtrpjfwkgbtn;//群体日平均访问
	
	SwitchButton qttzkgbtn;//群体体重开关
	SwitchButton tzbhkgbtn;//体重变化开关
	SwitchButton tzbhqskgbtn;//体重变化趋势开关
	SwitchButton cltzkgbtn;//出栏体重开关
	
	Button sfwkgbtn;
	Button tzbtn;
	Button slbtn;
	Button lrbbtn;
	Button fwbtn;
	Button wssdbtn;
	
	int tzbtntag = 1;
	int slbtntag = 1;
	int lrbbtntag = 1;
	int fwbtntag = 1;
	
    private TextView textview_title;
    private Button button_back;
    private Button button_save;
    private SharePreferenceUtil mSpUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhi_xxts);
		initView();
		initData();
	}

	private void initView() {
		textview_title = (TextView) findViewById(R.id.textview_title);
		button_back = (Button) findViewById(R.id.button_back);
		button_save = (Button) findViewById(R.id.button_save);
		
		xxxkgbtn = (SwitchButton) findViewById(R.id.xxxkg);// 接收新消息通知
		wdkgbtn = (SwitchButton) findViewById(R.id.wdkg);// 温度
		wlkgbtn = (SwitchButton) findViewById(R.id.wlkg);// 网络
		sjkgbtn = (SwitchButton) findViewById(R.id.sjkg);// 事件
		sbkgbtn = (SwitchButton) findViewById(R.id.sbkg);// 设备
		tzbtn = (Button) findViewById(R.id.tzbtn);// 体重
		slbtn = (Button) findViewById(R.id.slbtn);// 饲料
		lrbbtn = (Button) findViewById(R.id.lrbbtn);// 料肉比
		fwbtn = (Button) findViewById(R.id.fwbtn);// 访问
		
		rslbhkgbtn = (SwitchButton) findViewById(R.id.rslbhkg);// 日饲料变化
		rslkgbtn = (SwitchButton) findViewById(R.id.rslkg);// 日饲料
		
		qclrbbhkgbtn = (SwitchButton) findViewById(R.id.qclrbbhkg);// 全程料肉比变化
		qclrbkgbtn = (SwitchButton) findViewById(R.id.qclrbkg);// 全程料肉比
		
		qtrpjfwbhkgbtn = (SwitchButton) findViewById(R.id.qtrpjfwbhkg);// 群体日平均访问变化
		qtrpjfwkgbtn = (SwitchButton) findViewById(R.id.qtrpjfwkg);// 群体日平均访问
		//sfwkgbtn = (Button) findViewById(R.id.sfwkg);// 时访问

		cltzkgbtn = (SwitchButton) findViewById(R.id.cltzkg);// 出栏体重
		tzbhqskgbtn = (SwitchButton) findViewById(R.id.tzbhqskg);// 体重变化趋势
		tzbhkgbtn = (SwitchButton) findViewById(R.id.tzbhkg);// 体重变化
		qttzkgbtn = (SwitchButton) findViewById(R.id.qttzkg);// 群体体重

		llayoutxxtz = (LinearLayout) findViewById(R.id.llayoutxxtz);
		wdlayout = (RelativeLayout) findViewById(R.id.wdlayout);
		llayouttz = (LinearLayout) findViewById(R.id.llayouttz);
		llayoutsl = (LinearLayout) findViewById(R.id.llayoutsl);
		llayoutlrb = (LinearLayout) findViewById(R.id.llayoutlrb);
		llayoutfw = (LinearLayout) findViewById(R.id.llayoutfw);
		tzlayout = (RelativeLayout) findViewById(R.id.tzlayout);
		sllayout = (RelativeLayout) findViewById(R.id.sllayout);
		fwlayout = (RelativeLayout) findViewById(R.id.fwlayout);
		lrblayout = (RelativeLayout) findViewById(R.id.lrblayout);
		
		button_back.setOnClickListener(this);
		tzlayout.setOnClickListener(this);
		sllayout.setOnClickListener(this);
		fwlayout.setOnClickListener(this);
		lrblayout.setOnClickListener(this);
		
		xxxkgbtn.setOnCheckedChangeListener(this);
		wdkgbtn.setOnCheckedChangeListener(this);
		wlkgbtn.setOnCheckedChangeListener(this);
		sjkgbtn.setOnCheckedChangeListener(this);
		sbkgbtn.setOnCheckedChangeListener(this);
		
		rslbhkgbtn.setOnCheckedChangeListener(this);
		rslkgbtn.setOnCheckedChangeListener(this);
		
		qclrbbhkgbtn.setOnCheckedChangeListener(this);
		qclrbkgbtn.setOnCheckedChangeListener(this);
		
		qtrpjfwbhkgbtn.setOnCheckedChangeListener(this);
		qtrpjfwkgbtn.setOnCheckedChangeListener(this);
		
		cltzkgbtn.setOnCheckedChangeListener(this);
		tzbhqskgbtn.setOnCheckedChangeListener(this);
		tzbhkgbtn.setOnCheckedChangeListener(this);
		qttzkgbtn.setOnCheckedChangeListener(this);
	}
	
	private void initData() {
		mSpUtil = HaifmPApplication.getInstance().getSpUtil();
		textview_title.setText("消息推送");
		button_save.setVisibility(View.INVISIBLE);
		String kgzt = mSpUtil.getKgzt();
		mSpUtil.setKgzt(kgzt);
		if (kgzt.length() != XtAppConstant.KGZT_LENGTH) {
			ToastMsg(this, "数据出错");
			finish();
			return;
		}
		if (kgzt.substring(0,1).equals(XtAppConstant.KG_ON)) {//第一位总开关
			xxxkgbtn.setChecked(true);
			llayoutxxtz.setVisibility(View.VISIBLE);
		} else {
			llayoutxxtz.setVisibility(View.GONE);
		}
		if (kgzt.substring(1,2).equals(XtAppConstant.KG_ON)) {//第2位温度开关
			wdkgbtn.setChecked(true);
		}
		if (kgzt.substring(2,3).equals(XtAppConstant.KG_ON)) {//第3位总开关
			wlkgbtn.setChecked(true);
		}
		if (kgzt.substring(3,4).equals(XtAppConstant.KG_ON)) {//第4位总开关
			sjkgbtn.setChecked(true);
		}
		if (kgzt.substring(4,5).equals(XtAppConstant.KG_ON)) {//第5位总开关
			sbkgbtn.setChecked(true);
		}
		if (kgzt.substring(5,6).equals(XtAppConstant.KG_ON)) {//第6位总开关
			qttzkgbtn.setChecked(true);
		}
		if (kgzt.substring(6,7).equals(XtAppConstant.KG_ON)) {//第7位总开关
			tzbhkgbtn.setChecked(true);
		}
		if (kgzt.substring(7,8).equals(XtAppConstant.KG_ON)) {//第8位总开关
			tzbhqskgbtn.setChecked(true);
		}
		if (kgzt.substring(8,9).equals(XtAppConstant.KG_ON)) {//第9位总开关
			cltzkgbtn.setChecked(true);
		}
		if (kgzt.substring(9,10).equals(XtAppConstant.KG_ON)) {//第10位总开关
			rslkgbtn.setChecked(true);
		}
		if (kgzt.substring(10,11).equals(XtAppConstant.KG_ON)) {//第11位总开关
			rslbhkgbtn.setChecked(true);
		}
		if (kgzt.substring(11,12).equals(XtAppConstant.KG_ON)) {//第12位总开关
			qclrbkgbtn.setChecked(true);
		}
		if (kgzt.substring(12,13).equals(XtAppConstant.KG_ON)) {//第13位总开关
			qclrbbhkgbtn.setChecked(true);
		}
		if (kgzt.substring(13,14).equals(XtAppConstant.KG_ON)) {//第14位总开关
			qtrpjfwkgbtn.setChecked(true);
		}
		if (kgzt.substring(14,15).equals(XtAppConstant.KG_ON)) {//第15位总开关
			qtrpjfwbhkgbtn.setChecked(true);
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			finish();
			break;
		case R.id.tzlayout: // 体重展开收缩
			if (tzbtntag == 0) {
				llayouttz.setVisibility(View.GONE);
				tzbtn.setBackgroundResource(R.drawable.xxts_arrow_right);
				tzbtntag = 1;
			} else {
				llayouttz.setVisibility(View.VISIBLE);
				tzbtn.setBackgroundResource(R.drawable.navigationbar_arrow_down);
				tzbtntag = 0;
			}
			break;
		case R.id.sllayout: // 饲料展开收缩
			dismissLoading();
			if (slbtntag == 0) {
				llayoutsl.setVisibility(View.GONE);
				slbtn.setBackgroundResource(R.drawable.xxts_arrow_right);
				slbtntag = 1;
			} else {
				llayoutsl.setVisibility(View.VISIBLE);
				slbtn.setBackgroundResource(R.drawable.navigationbar_arrow_down);
				slbtntag = 0;
			}
			break;
		case R.id.lrblayout: // 料肉比展开收缩
			dismissLoading();
			if (lrbbtntag == 0) {
				llayoutlrb.setVisibility(View.GONE);
				lrbbtn.setBackgroundResource(R.drawable.xxts_arrow_right);
				lrbbtntag = 1;
			} else {
				llayoutlrb.setVisibility(View.VISIBLE);
				lrbbtn.setBackgroundResource(R.drawable.navigationbar_arrow_down);
				lrbbtntag = 0;
			}
			break;
		case R.id.fwlayout: // 访问展开收缩
			if (fwbtntag == 0) {
				llayoutfw.setVisibility(View.GONE);
				fwbtn.setBackgroundResource(R.drawable.xxts_arrow_right);
				fwbtntag = 1;
			} else {
				llayoutfw.setVisibility(View.VISIBLE);
				fwbtn.setBackgroundResource(R.drawable.navigationbar_arrow_down);
				fwbtntag = 0;
			}
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.xxxkg://总开关
			if (isChecked) {
				llayoutxxtz.setVisibility(View.VISIBLE);
				ModifyKgzt(0, Integer.parseInt(XtAppConstant.KG_ON));
			} else {
				llayoutxxtz.setVisibility(View.GONE);
				ModifyKgzt(0, Integer.parseInt(XtAppConstant.KG_OFF));
			}
			break;
		case R.id.wdkg: // 温度
			setKgztSp(1, isChecked);
			break;
		case R.id.wlkg: // 网络
			setKgztSp(2, isChecked);
			break;
		case R.id.sjkg: // 事件
			setKgztSp(3, isChecked);
			break;
		case R.id.sbkg: // 设备
			setKgztSp(4, isChecked);
			break;
		case R.id.qttzkg: // 群体体重
			setKgztSp(5, isChecked);
			break;
		case R.id.tzbhkg: // 体重变化
			setKgztSp(6, isChecked);
			break;
		case R.id.tzbhqskg: // 体重变化趋势
			setKgztSp(7, isChecked);
			break;
		case R.id.cltzkg: // 出栏体重
			setKgztSp(8, isChecked);
			break;
		case R.id.rslkg: // 日饲料
			setKgztSp(9, isChecked);
			break;
		case R.id.rslbhkg: // 日饲料变化
			setKgztSp(10, isChecked);
			break;
		case R.id.qclrbkg: // 全程料肉比
			setKgztSp(11, isChecked);
			break;
		case R.id.qclrbbhkg: // 全程料肉比变化
			setKgztSp(12, isChecked);
			break;
		case R.id.qtrpjfwkg: // 群体日平均访问
			setKgztSp(13, isChecked);
			break;
		case R.id.qtrpjfwbhkg: // 群体日平均访问变化
			setKgztSp(14, isChecked);
			break;
		case R.id.sfwkg: // 时访问
			break;
		}
	}
	/**
	 * 设置改变的消息位
	 * @param start
	 * @param isChecked
	 */
	private void setKgztSp(int start, boolean isChecked) {
		if (isChecked) {
			ModifyKgzt(start, Integer.parseInt(XtAppConstant.KG_ON));
		} else {
			ModifyKgzt(start, Integer.parseInt(XtAppConstant.KG_OFF));
		}
	}
	
	/**
	 * 截取消息位进行改变
	 * @param start
	 * @param kgztInt
	 */
	private void ModifyKgzt(int start, int kgztInt) {
		if (String.valueOf(kgztInt) .equals(XtAppConstant.KG_OFF)) {
			mSpUtil.setKgzt(mSpUtil.getKgzt().substring(0, start) + XtAppConstant.KG_OFF + mSpUtil.getKgzt().substring(start + 1));
		} else {
			mSpUtil.setKgzt(mSpUtil.getKgzt().substring(0, start) + XtAppConstant.KG_ON + mSpUtil.getKgzt().substring(start + 1));
		}
	}
}
