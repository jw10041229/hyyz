package com.huimv.szmc.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huimv.android.basic.base.BaseActivity;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.R;

public class ErweimaWebViewActivity extends BaseActivity {
    public static WebView mWebView;
    private ProgressBar mProgressBar;
    private String url = "https://hao.360.cn/?src=lm&ls=n580386de8b";
    private TextView textview_title;
    private Button button_back;
    private Button button_save;
    private RelativeLayout include_activity_title_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_fragment);
	    HaifmPApplication.addActivity(this);
	    include_activity_title_back = (RelativeLayout) findViewById(R.id.include_activity_title_back);
	    include_activity_title_back.setVisibility(View.VISIBLE);
	    mWebView = (WebView) findViewById(R.id.mainWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        textview_title = (TextView) findViewById(R.id.textview_title);
        textview_title.setText(getString(R.string.right_title_erweima));
        button_back = (Button) findViewById(R.id.button_back);
		button_save = (Button) findViewById(R.id.button_save);
		button_save.setVisibility(View.GONE);
        button_back.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mProgressBar.setMax(100);
        initWebView(url);
	}
	
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false); // 显示放大缩小 controler
        settings.setSupportZoom(false); // 可以缩放
        settings.setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //设置Web视图 
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 10) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.setWebViewClient(new mWebViewClient ()); 
        mWebView.loadUrl(url);
    }
    //Web视图 
    private class mWebViewClient extends WebViewClient { 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return true; 
        } 
    }
}
