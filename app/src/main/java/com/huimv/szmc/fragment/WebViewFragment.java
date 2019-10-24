package com.huimv.szmc.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import com.huimv.android.basic.base.BaseFragment;
import com.huimv.szmc.R;
import com.huimv.szmc.constant.XtAppConstant;


public class WebViewFragment extends BaseFragment {
    public static WebView mWebView;
    private ProgressBar mProgressBar;
    private String url = "/ifm/getXlh.htm?xlh=";
    private TextView textview_title;
    private Button button_back,button_refresh;
    private Button button_save;
    private RelativeLayout include_activity_title_back;
    public static final String ARGUMENT = "argument";
    private String mArgument;
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
        mWebView.loadUrl("http://" + XtAppConstant.SERVICE_IP + url + mArgument);
    }

    @Override
    public View onInitView(LayoutInflater inflater, ViewGroup rootView, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }
        View mView = inflater.inflate(R.layout.webview_fragment, null);
        button_refresh = (Button)mView.findViewById(R.id.button_refresh);
        button_refresh.setVisibility(View.GONE);
        include_activity_title_back = (RelativeLayout) mView.findViewById(R.id.include_activity_title_back);
        include_activity_title_back.setVisibility(View.GONE);
        mWebView = (WebView) mView.findViewById(R.id.mainWebView);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.mProgressBar);
        textview_title = (TextView) mView.findViewById(R.id.textview_title);
        textview_title.setText(mArgument);
        button_back = (Button) mView.findViewById(R.id.button_back);
        button_save = (Button) mView.findViewById(R.id.button_save);
        button_save.setVisibility(View.GONE);
        button_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mProgressBar.setMax(100);
        initWebView(url);
        return  mView;
    }

    //Web视图
    private class mWebViewClient extends WebViewClient { 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return true; 
        } 
    }

    /**
     * 传入需要的参数，设置给arguments
     * @param argument
     * @return
     */
    public static WebViewFragment newInstance(String argument)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        WebViewFragment contentFragment = new WebViewFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
}
