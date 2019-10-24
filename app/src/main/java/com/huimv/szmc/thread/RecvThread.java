package com.huimv.szmc.thread;

import com.huimv.szmc.constant.XtAppConstant;
import com.huimv.szmc.util.YzzsCommonUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jiangwei-pc on 2017/4/19.
 */
public class RecvThread extends Thread {
    public static ArrayList<EventHandler> ehList = new ArrayList<>();
    public interface EventHandler {
        void onRecvMessage(byte[] message);//接收到数据
    }
    private byte[] tempData;//临时包
    private byte [] messageData = new byte[0];//截取出来下发的整条命令
    @Override
    public void run() {
       super.run();
        try {
            while(!isInterrupted()){
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取一条完整的命令
    private void getCompleteCMD() {
        if (tempData.length > 7) {//必须有包头包尾校验位
            for (int i = 0; i < tempData.length - 2; i++) {
                //包头校验
                if (i == 0 && !checkBt()) {
                    break;
                }
                //包尾存在
                if (tempData[i] == XtAppConstant.packBottomE &&
                        tempData[i + 1] == XtAppConstant.packBottomN &&
                        tempData[i + 2] == XtAppConstant.packBottomD) {
                    //获取第一次正确的协议
                    messageData = Arrays.copyOfRange(tempData, 0, i + 3);
                    //继续查询协议
                    tempData = Arrays.copyOfRange(tempData, i + 3, tempData.length);
                    //下发协议
                    xfLoad();
                    //重新循环
                    i = -1;
                }
            }
            //对剩余包进行处理
            for (int i = tempData.length - 1; i > 0; i--) {
                if (tempData[i] == XtAppConstant.packHeadM &&
                        tempData[i - 1] == XtAppConstant.packHeadH) {
                    tempData = Arrays.copyOfRange(tempData, i - 1 , tempData.length);
                    break;
                }
            }
        }
    }
    //检验包头
    private boolean checkBt() {  {
            boolean flag = false;
            //包头校验
            if (!(tempData [0] == XtAppConstant.packHeadH &&
                    tempData [1] == XtAppConstant.packHeadM)) {
                //如果出现丢掉包头，那么从下一个包头开始协议
                for (int i = 0; i < tempData.length; i++) {
                    //包头满足第一位
                    if (tempData[i] == XtAppConstant.packHeadH) {
                        //当满足第一位且为最后一位 向下 否则截取进行判断
                        if (i == tempData.length - 1) {
                            tempData = Arrays.copyOfRange(tempData, i, tempData.length);
                            break;
                        }
                        if (i != tempData.length - 1 && tempData[i + 1] == XtAppConstant.packHeadM) {
                            tempData = Arrays.copyOfRange(tempData, i, tempData.length);
                            flag = true;
                            break;
                        }
                    }
                }
            } else {
                flag = true;
            }
            return flag;
        }
    }
    /**
     * 下发
     */
    public void xfLoad() {
        if (messageData.length > 7) {
            int length1 = YzzsCommonUtil.ChangeByteToPositiveNumber(messageData[5]);
            int length2 = YzzsCommonUtil.ChangeByteToPositiveNumber(messageData[6]);
            int length = YzzsCommonUtil.getCMD(length1, length2);
            if (length == messageData.length) {//拼包结束
                messageData = Arrays.copyOfRange(messageData, 2, length - 4);//去协议头尾跟校验位
                dispatchData(messageData);
                Logger.d("RecvThread",Arrays.toString(messageData));
            } else {
            }
        }
    }
    //下发数据到观察者
    private void dispatchData(byte[] mDispatchData) {
        if (mDispatchData != null) {
            for (int i = 0; i < ehList.size(); i++) {
                ehList.get(i).onRecvMessage(mDispatchData);
            }
        }
    }
}
