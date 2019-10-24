package com.huimv.szmc.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;


/**
 * Created by jiangwei-pc on 2017/7/6.
 */

public class RIFDTempCalculateUtil {
    private static String REGEX_SPACE = " "; // 空格
    private static DecimalFormat df = new DecimalFormat("######0.00");//两位小数
    private static String temp = "";

    /**
     * 计算temp
     * @param context
     * @param APsw
     * @param realData
     * @return
     */
    public static String tempCal (Context context,String APsw,byte [] realData) {
        String ABCValue = APsw.substring(16);
        if ("".equals(APsw) || ABCValue.length() != 8) {
            Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show();
        } else {
            String StrTemp = bytesToHexString(realData, REGEX_SPACE);
            int code = Integer.parseInt(StrTemp.split(REGEX_SPACE)[0].substring(1, 2) + StrTemp.split(REGEX_SPACE)[1], 16);
            String A = ABCValue.substring(0, 2);
            String B = ABCValue.substring(2, 4);
            String C = ABCValue.substring(4, 6);
            if (A.equals("00") && B.equals("00") && C.equals("00")) {
                A = "97";
                B = "70";
                C = "55";
            }
            temp = df.format(Double.valueOf("12" + A) / 10000 * code - Double.valueOf("2" + B + C) / 100) + "";
        }
        return temp;
    }
    public static String RFIDCal (String APsw) {
        boolean isOK;
        if (APsw.length() < 16) {
            isOK = false;
        } else
            isOK = true;
        return isOK ? APsw.substring(0,15) : "";
    }

    public static String getAps (byte [] realData) {

        return bytesToHexString(realData, REGEX_SPACE).replace(" ", "");
    }
    /**
     * byte[]转换成十六进制字符串
     *
     * @param bArray
     * @param regex
     * @return
     */
    public static String bytesToHexString(byte[] bArray, String regex) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase() + regex);
        }
        // 去掉最后一个 regex
        sb.substring(0, sb.length() - 1);
        return sb.toString();
    }
}
