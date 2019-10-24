package com.huimv.szmc.util;

import java.util.Arrays;
import java.util.List;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.Spanned;

/**
 * @author jiangwei
 * 
 */
public class HaifmCommonUtil {
	Context context;
	String url;
	Spanned msg;

	public HaifmCommonUtil(Context context, Spanned msg, String url) {
		this.context = context;
		this.msg = msg;
		this.url = url;
	}

	/** 得到APP版本号 */
	public static String getVersionName(Context context) {
		String loadVersionName = "";
		PackageManager nPackageManager = context.getPackageManager();// 得到包管理器
		try {
			PackageInfo nPackageInfo = nPackageManager
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
			loadVersionName = nPackageInfo.versionName;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		return loadVersionName;
	}

	/**
	 * 时间格式后面秒补 ":00"
	 * 
	 * @param date
	 * @return
	 */
	public static String dateAdd00(String date) {
		return new StringBuffer(date).append(":00").toString();
	}

	/**
	 * 根据值 去除数组中的相同值
	 */
	public static String[] deleteArrByValue(String[] arr, String param) {
		String[] strArr = Arrays.copyOf(arr, arr.length);
		boolean flag = false;
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].equals(param) && !flag) {
				flag = true;
			}
			if (flag && i < strArr.length - 1) {
				strArr[i] = strArr[i + 1];
			}
		}
		int len;
		if (flag) {
			len = strArr.length - 1;
		} else {
			len = strArr.length;
		}
		return Arrays.copyOf(strArr, len);
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param
	 * 
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(100);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(
					"com.huimv.haifm.service." + className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/** 更新 */
	public void exitDialog() {
		new AlertDialog.Builder(context)
				.setMessage(msg)
				.setTitle("更新提示")
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Uri u = Uri.parse(url);
						Intent it = new Intent(Intent.ACTION_VIEW, u);
						context.startActivity(it);
						android.os.Process.killProcess(android.os.Process
								.myPid());// 获取当前进程PID，并杀掉
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
	}



}