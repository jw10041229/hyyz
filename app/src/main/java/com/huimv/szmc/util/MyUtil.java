package com.huimv.szmc.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;

@SuppressLint("SimpleDateFormat")
public class MyUtil {
	Context context;
	String url;
	Spanned msg;

	public MyUtil() {
	};

	public MyUtil(Context context, Spanned msg, String url) {
		this.context = context;
		this.msg = msg;
		this.url = url;
	}

	/** 将JSON字符串转换为Map */
	public static Map<String, Object> getMap(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 将JSON字符串转换为ArrayList */
	public static List<Map<String, Object>> getList(String jsonString) {
		List<Map<String, Object>> list = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			JSONObject jsonObject;
			list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObject.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/** 将double转换为年月日 */
	public static String DatetoString(double d) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd ");
		return sdf.format(d);
	}

	public static String DatetoStringMD(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("M/d ");
		return sdf.format(d);
	}

	public static String DatetoStringHM(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
		return sdf.format(d);
	}

	public static String DatetoStringYMHM(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm");
		return sdf.format(d);
	}
	public static String DatetoStringYMHM2(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		return sdf.format(d);
	}
	public static String doubletoString(double d) {
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
		return sdf.format(d);
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

	/**
	 * 从指定的几个数组中获取最大值或最小值
	 * @param res1：数组1
	 * @param res2：数组2
	 * @param res3：数组3
	 * @param flag：1、获取最大值	2、获取最小值
	 * @return
	 */
	public static double getMaxMin(double[] res1, double[] res2, double[] res3, int flag) {
		double[] temp1 = res1.clone();
		double[] temp2= res2.clone();
		double[] temp3 = res3.clone();
		Arrays.sort(temp1);
		Arrays.sort(temp2);
		Arrays.sort(temp3);
		
		if (flag == 1) {
			return Math.max(Math.max(temp1[temp1.length - 1], temp2[temp2.length - 1]), temp3[temp3.length - 1]);
		} else {
			return Math.min(Math.min(temp1[0], temp2[0]), temp3[0]);
		}
	}
	
	/**
	 * 从一个数组中获取最大值或最小值
	 * @param res1：数组1不为空
	 * @param flag：1、获取最大值	2、获取最小值
	 * @return
	 */
	public static double getMaxMin(double[] res1, int flag) {
		double[] temp1 = res1.clone();
		Arrays.sort(temp1);
		if (flag == 1) {
			return temp1[temp1.length-1] ;
		} else {
			return temp1[0];
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            符合格式的日期
	 * @return 格式后的日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parser(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.clear(Calendar.MILLISECOND);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.HOUR_OF_DAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 根据 flag 将 str 格式化补0
	 * @param str
	 * @param length 补上之后的总长度
	 * @param flag：1、左补0 2、右补0
	 * @return
	 */
	public static String formatStringAdd0(String str, int length, int flag) {
		if (str == null || "".equals(str) || str.length() >= length) {
			return str;
		}

		int j = str.length();
		for (int  i = 0; i < length - j; i++) {
			str = flag == 1 ? "1" + str : str + "1";
		}
		
		return str;
	}

}
