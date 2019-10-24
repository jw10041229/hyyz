package com.huimv.szmc.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huimv.android.basic.base.BaseActivity;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.android.basic.util.PhoneUtil;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

public class ShoujihaoModifyActivity extends BaseActivity implements OnClickListener{
    private Button button_back;
    private TextView textview_title;
    private Button button_save;
    private ImageView deleteImageView;
    private EditText ShoujihaoEditText;
    private SharePreferenceUtil mSpUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_right_item_shoujihao);
	    init();
	    ShoujihaoEditText.setText(mSpUtil.getShoujihao());
	}
	
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteImageView:
                ShoujihaoEditText.setText("");
                break;
            case R.id.button_back:
                finish();
                break;
            case R.id.button_save:
                if (CommonUtil.isEmpty(ShoujihaoEditText.getText().toString().trim())) {
                    ToastMsg(ShoujihaoModifyActivity.this, getString(R.string.shoujihao_notNull));
                    return;
                }
                if (!PhoneUtil.isMobileNO(ShoujihaoEditText.getText().toString().trim())) {
                    ToastMsg(ShoujihaoModifyActivity.this, getString(R.string.shoujihao_notFill));
                    return;
                }
                // TODO haifm 这里是上传云平台的手机号修改，修改成功后再存入SP
                mSpUtil.setShoujihao(ShoujihaoEditText.getText().toString().trim());
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
        ShoujihaoEditText = (EditText) findViewById(R.id.shoujihaoEditText);
        textview_title.setText(getString(R.string.right_title_shoujihao));
        HaifmPApplication.addActivity(this);
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        deleteImageView.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_save.setOnClickListener(this);
    }
}
