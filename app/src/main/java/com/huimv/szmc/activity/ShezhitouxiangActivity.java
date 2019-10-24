package com.huimv.szmc.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huimv.android.basic.base.BaseActivity;
import com.huimv.szmc.app.HaifmPApplication;
import com.huimv.szmc.bean.UploadTouxiangBean;
import com.huimv.szmc.client.WsCmd;
import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.support.ShezhitouxiangActivitySupport;
import com.huimv.szmc.util.BitmapUtil;
import com.huimv.szmc.util.SharePreferenceUtil;
import com.huimv.szmc.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShezhitouxiangActivity extends BaseActivity implements Callback ,OnClickListener{
    public static final String TAG = ShezhitouxiangActivity.class.getSimpleName();
	private static SharePreferenceUtil mSpUtil;
	private Button backButton;
	private TextView titleTextView;
	private Button saveButton;
	private ImageView touxiangImageView;
	private RelativeLayout sztx_xiangceRL;
	private RelativeLayout sztx_paizhaoRL;
    private Uri fileUri;
    private Gson gson = new Gson();
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PHOTO_WITH_DATA = 101;
    public static final int MEDIA_TYPE_XIANGCE = 2;
    private static final int PHOTO_CROP = 3;//裁剪图片
    public static final String HAIFMPIC = "HAIFMPIC_";
    public static String path = Environment.getExternalStorageDirectory().getPath() + "/hafimHeadPic/";//sd路径
    private Bitmap head;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhi_touxiang);
	    initView();
	    initData();
	}

	private void initData() {
        HaifmPApplication.addActivity(this);
        mSpUtil = HaifmPApplication.getInstance().getSpUtil();
        titleTextView.setText(getString(R.string.sztx_title));
	    Bitmap bt = BitmapFactory.decodeFile(mSpUtil.getHeadPicUrl());//从Sd中找头像，转换成Bitmap
        if( bt != null ) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            touxiangImageView.setImageDrawable(drawable);
        } else {
            /**
             *  如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             * 
             */
        }
    }

    private void initView() {
	    backButton = (Button) findViewById(R.id.button_back);
	    titleTextView = (TextView) findViewById(R.id.textview_title);
	    saveButton = (Button) findViewById(R.id.button_save);
	    touxiangImageView = (ImageView) findViewById(R.id.touxiangImageView);
	    sztx_xiangceRL = (RelativeLayout) findViewById(R.id.sztx_xiangceRL);
	    sztx_paizhaoRL = (RelativeLayout) findViewById(R.id.sztx_paizhaoRL);
	    saveButton.setVisibility(View.INVISIBLE);
	    backButton.setOnClickListener(this);
	    sztx_xiangceRL.setOnClickListener(this);
	    sztx_paizhaoRL.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case XtAppConstant.WHAT_UPLOAD_TOUXIANG_THREAD:
            	dismissLoading();
            	if (msg.arg1 == WsCmd.SUCCESS) {
            		setPicToView(head,mSpUtil);//保存在SD卡中
            		touxiangImageView.setImageBitmap(head);
            	}
            	if (msg.arg1 == WsCmd.PARAM_ERROR) {
            		ToastMsg(ShezhitouxiangActivity.this, "头像上传失败,请重试");
            	}
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
               finish();
                break;
            case R.id.sztx_xiangceRL:
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, MEDIA_TYPE_XIANGCE);
                break;
            case R.id.sztx_paizhaoRL:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
            default:
                break;
        }
        super.onClick(v);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE://拍照
                if (resultCode == RESULT_OK) {
                    cropPhoto(fileUri);
                }
                break;
            case MEDIA_TYPE_XIANGCE://相册
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case PHOTO_WITH_DATA:
                break;
            case PHOTO_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head != null) {
                        /**
                         * 上传服务器代码
                         */
                    	String bitmapBase64Str = BitmapUtil.Bitmap2StrByBase64(head, 50);//这里采用压缩质量来二次压缩
                    	UploadTouxiangBean mUpdateTouxiangBean = new UploadTouxiangBean();
                    	mUpdateTouxiangBean.setYhid(mSpUtil.getUserID());
                    	mUpdateTouxiangBean.setYhtx(bitmapBase64Str);
                    	String param = gson.toJson(mUpdateTouxiangBean);
                    	showLoading();
                    	ShezhitouxiangActivitySupport.UploadThread(param, ShezhitouxiangActivity.this, new Handler(ShezhitouxiangActivity.this));
                    }
                }
                break;
            default:
                break;
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile((new File(path,HAIFMPIC + mSpUtil.getUserID() + XtAppConstant.JPG)));
    }

    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
         // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP);
    }
    
    //保存到SD卡
    public static void setPicToView(Bitmap mBitmap,SharePreferenceUtil mSpUtil) {
        String sdStatus = Environment.getExternalStorageState();  
       if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
           //提示SD卡不可用
              return;  
          }  
       FileOutputStream b = null;
       File file = new File(path);
       file.mkdirs();// 创建文件夹
       String fileName = path + HAIFMPIC + mSpUtil.getUserID() + XtAppConstant.JPG;//图片名字
       mSpUtil.setHeadPicUrl(fileName);
       try {
           b = new FileOutputStream(fileName);
           mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } finally {
           try {
               //关闭流
               b.flush();
               b.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
}
}
