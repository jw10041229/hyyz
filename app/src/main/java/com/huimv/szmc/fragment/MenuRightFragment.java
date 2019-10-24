package com.huimv.szmc.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huimv.android.basic.base.BaseFragment;
import com.huimv.android.basic.util.AsyncImageLoader;
import com.huimv.android.basic.util.CommonUtil;
import com.huimv.szmc.R;
import com.huimv.szmc.activity.ErweimaActivity;
import com.huimv.szmc.activity.ErweimaWebViewActivity;
import com.huimv.szmc.activity.ModifyPasswordActivity;
import com.huimv.szmc.activity.MuChangSetActivity;
import com.huimv.szmc.activity.NichengModifyActivity;
import com.huimv.szmc.activity.ShezhitouxiangActivity;
import com.huimv.szmc.activity.XxtsShezhiActivity;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.messageEvent.MessageEvent;
import com.huimv.szmc.util.MD5Util;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.view.BorderImageView;
import com.huimv.szmc.zxing.view.TxmScanActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MenuRightFragment extends BaseFragment implements Callback, OnClickListener,EasyPermissions.PermissionCallbacks{
    private View mView;
    private Button exitButton;
    private BorderImageView touxiang;
    private RelativeLayout touxiangRL;
    private RelativeLayout nichengRL;
    private RelativeLayout zhanghuRL;
    private RelativeLayout shoujihaoRL;
    private RelativeLayout mimashezhiRL;
    private RelativeLayout xiaoxitongzhiRL;
    private RelativeLayout saoyisaoRL;
    private RelativeLayout erweimaRL;
    private RelativeLayout rl_mcsz;
    private RelativeLayout rl_ewm;
    private RelativeLayout rl_ydjrq;
    private Intent intent = new Intent();
    private TextView nichengTextView;
    private TextView zhanghuTextView;
    private SharePreferenceUtil mSpUtil;
    private String ImageUrl = "";
    private String youkeTag = "";
    public static final String PRAMA = "prama";
    private static final int RC_CAMERA = 0x05;//存储权限
    @Override
    public View onInitView(LayoutInflater inflater, ViewGroup rootView, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.right_menu, null);
        initView();
        initData();
        return mView;
    }

    @SuppressWarnings("deprecation")
    private void initData() {
    	youkeTag = mSpUtil.getYoukeTag();
        if (!CommonUtil.isEmpty(youkeTag)) {
            if (youkeTag.equals(XtAppConstant.YOUKE)) {
                nichengTextView.setText(getString(R.string.visit_account));
                zhanghuTextView.setText("账户:" + getString(R.string.visit_account));
                return;
            } 
            if (youkeTag.equals(XtAppConstant.ZHUCE)) {
                nichengTextView.setText(mSpUtil.getNicheng());
                zhanghuTextView.setText("账户:" + mSpUtil.getAccount());
            }
        }
    	ImageUrl = XtAppConstant.HTTP + XtAppConstant.SERVICE_IP + XtAppConstant.SERVICE_TOUXIANG_FILENAME + 
    			mSpUtil.getUserID() + XtAppConstant.JPG;
    	//TODO  haifm 这个地方逻辑有点问题，应该和服务器通信，得到有无改变，如果有则从服务器下载，没有则到本地找
    	new AsyncImageLoader().downloadImage(ImageUrl,false, new AsyncImageLoader.ImageCallback() {
			
			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl) {
				if (bitmap != null) {
					ShezhitouxiangActivity.setPicToView(bitmap,mSpUtil);
					Drawable drawable = new BitmapDrawable(getResources(), bitmap);//转换成drawable
					touxiang.setBackgroundDrawable(drawable);
				} else {
					Bitmap bt = BitmapFactory.decodeFile(mSpUtil.getHeadPicUrl());//从Sd中找头像，转换成Bitmap
					 if( bt != null ) {
				            Drawable drawable = new BitmapDrawable(getResources(), bt);//转换成drawable
				            //touxiang.setImageDrawable(drawable);
				            touxiang.setBackgroundDrawable(drawable);
				           //HaifmPApplication.initImageLoad(touxiang, mSpUtil.getHeadPicUrl());
				        }
				}
			}
		});
        /*Bitmap bt = BitmapFactory.decodeFile(mSpUtil.getHeadPicUrl());//从Sd中找头像，转换成Bitmap
        if( bt != null ) {
            Drawable drawable = new BitmapDrawable(getResources(), bt);//转换成drawable
            //touxiang.setImageDrawable(drawable);
            touxiang.setBackgroundDrawable(drawable);
           //HaifmPApplication.initImageLoad(touxiang, mSpUtil.getHeadPicUrl());
        } else {
            *//**
             *  如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             * 
             *//*
        	new AsyncImageLoader().downloadImage(ImageUrl,false, new ImageCallback() {
				
				@Override
				public void onImageLoaded(Bitmap bitmap, String imageUrl) {
					if (bitmap != null) {
						ShezhitouxiangActivity.setPicToView(bitmap,mSpUtil);
						Drawable drawable = new BitmapDrawable(getResources(), bitmap);//转换成drawable
						touxiang.setBackgroundDrawable(drawable);
					}
				}
			});
        }*/
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
    	super.onResume();
    	if (youkeTag.equals(XtAppConstant.YOUKE)) {
    		return;
    	}
    	nichengTextView.setText(mSpUtil.getNicheng());
        Bitmap bt = BitmapFactory.decodeFile(mSpUtil.getHeadPicUrl());//从Sd中找头像，转换成Bitmap
        if( bt != null ) {
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            touxiang.setBackgroundDrawable(drawable);
        } else {
            /***如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中**/
        	new AsyncImageLoader().downloadImage(ImageUrl, false, new AsyncImageLoader.ImageCallback() {
				
				@Override
				public void onImageLoaded(Bitmap bitmap, String imageUrl) {
					if (bitmap != null) {
						ShezhitouxiangActivity.setPicToView(bitmap,mSpUtil);
						Drawable drawable = new BitmapDrawable(bitmap);//转换成drawable
						touxiang.setBackgroundDrawable(drawable);
					}
				}
			});
        }
    }

    private void initView() {
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        touxiangRL = (RelativeLayout) mView.findViewById(R.id.register_zhanghuRL);
        nichengRL = (RelativeLayout) mView.findViewById(R.id.sztx_xiangceRL);
        zhanghuRL = (RelativeLayout) mView.findViewById(R.id.register_shoujihaoRL);
        shoujihaoRL = (RelativeLayout) mView.findViewById(R.id.register_yanzhengmaRL);
        mimashezhiRL = (RelativeLayout) mView.findViewById(R.id.register_mimaRL);
        xiaoxitongzhiRL = (RelativeLayout) mView.findViewById(R.id.register_mimaConfirmRL);
        saoyisaoRL = (RelativeLayout) mView.findViewById(R.id.saoyisaoRL);
        rl_mcsz = (RelativeLayout) mView.findViewById(R.id.rl_mcsz);
        rl_ewm = (RelativeLayout) mView.findViewById(R.id.rl_ewm);
        exitButton = (Button) mView.findViewById(R.id.exitButton);
        nichengTextView = (TextView) mView.findViewById(R.id.nichengTextView);
        touxiang = (BorderImageView) mView.findViewById(R.id.touxiang);
        zhanghuTextView = (TextView) mView.findViewById(R.id.zhanghuTextView);
        rl_ydjrq = (RelativeLayout) mView.findViewById(R.id.rl_ydjrq_zc);
        exitButton.setOnClickListener(this);
        touxiangRL.setOnClickListener(this);
        nichengRL.setOnClickListener(this);
        zhanghuRL.setOnClickListener(this);
        shoujihaoRL.setOnClickListener(this);
        mimashezhiRL.setOnClickListener(this);
        xiaoxitongzhiRL.setOnClickListener(this);
        saoyisaoRL.setOnClickListener(this);
        rl_mcsz.setOnClickListener(this);
        rl_ewm.setOnClickListener(this);
        rl_ydjrq.setOnClickListener(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.exitButton && v.getId() != R.id.saoyisaoRL 
                && v.getId() != R.id.erweimaRL 
                && mSpUtil.getYoukeTag().equals(XtAppConstant.YOUKE)) {
            ToastMsg(getActivity(), "当前身份为游客不能进行相关操作");
            return;
        }
        switch (v.getId()) {
            case R.id.register_zhanghuRL:
                intent.setClass(getActivity(), ShezhitouxiangActivity.class);
                startActivity(intent);
                break;
            case R.id.sztx_xiangceRL:  
                intent.setClass(getActivity(), NichengModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_ewm:
                intent.setClass(getActivity(), ErweimaActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mcsz :
                intent.setClass(getActivity(), MuChangSetActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_ydjrq_zc :
                Intent intentYdjrq = new Intent();
                intentYdjrq.setClass(getActivity(), TxmScanActivity.class);
                MenuRightFragment.this.startActivityForResult(intentYdjrq, 1);
                //requestCarama();
                break;
            case R.id.register_yanzhengmaRL:
              /* intent.setClass(getActivity(), ShoujihaoModifyActivity.class);
                startActivity(intent);*/
            	ToastMsg(getActivity(), "此功能当前不可用");
                /*final EditText etIP = new EditText(getActivity());
                etIP.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(getActivity())
                        .setMessage("请输入ip")
                        .setTitle("webIP修改")
                        .setView(etIP)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (CommonUtil.isEmpty(etIP.getText().toString())) {
                                    ToastMsg(getActivity(), "IP");
                                    return;
                                }
                                mSpUtil.setTestIP(etIP.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();*/
                break;
            case R.id.register_mimaRL:
            	//ToastMsg(getActivity(), "此功能当前不可用");
                final EditText et = new EditText(getActivity());
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                new AlertDialog.Builder(getActivity())
                .setMessage("为了保证账户安全,修改密码前请输入原密码")
                .setTitle("密码修改提示")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (CommonUtil.isEmpty(et.getText().toString())) {
                            ToastMsg(getActivity(), "原密码不能为空");
                            return;
                        }
                        if (!MD5Util.crypt(et.getText().toString()).equals(mSpUtil.getPassword())) {
                            //L.d("MD5TAG", MD5Util.crypt(et.getText().toString()));
                            //L.d("MD5TAG", mSpUtil.getPassword());
                            ToastMsg(getActivity(), "输入的原密码不正确");
                            return;
                        } else {
                            dialog.dismiss();
                            intent.setClass(getActivity(), ModifyPasswordActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
            case R.id.saoyisaoRL:
                intent.setClass(getActivity(), TxmScanActivity.class);
                MenuRightFragment.this.startActivityForResult(intent, 1);
                break;
            case R.id.erweimaRL:
                intent.setClass(getActivity(), ErweimaWebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.register_mimaConfirmRL:
                intent.setClass(getActivity(), XxtsShezhiActivity.class);
                startActivity(intent);
                break;
            case R.id.exitButton:
                mSpUtil.setPassword("");
                mSpUtil.setIsLogined("0");
               /* intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);*/
                EventBus.getDefault().postSticky(new MessageEvent(2));
                break;
            default:
                break;
        }
        super.onClick(v);
    }
    
    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case 1:
            if (resultCode == getActivity().RESULT_OK) {
                Bundle bundle = data.getExtras();
                //ToastMsg(getActivity(),bundle.getString("txm"));
                String parama = bundle.getString("txm");
                EventBus.getDefault().postSticky(new MessageEvent(1,parama));
            }
            break;
        default:
            break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @AfterPermissionGranted(RC_CAMERA)
    public void requestCarama() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            Intent intentYdjrq = new Intent();
            intentYdjrq.setClass(getActivity(), TxmScanActivity.class);
            MenuRightFragment.this.startActivityForResult(intentYdjrq, 1);
        } else {
            EasyPermissions.requestPermissions(this, "需要相机权限", RC_CAMERA, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
    };
}
