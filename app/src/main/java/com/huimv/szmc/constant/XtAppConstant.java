package com.huimv.szmc.constant;

public interface XtAppConstant {
	/** file**/
	String FILE = "file://";
	/** IP**/
	//String SERVICE_IP = "192.168.1.180:8080";//服务器IP
	//String SERVICE_IP = "120.26.91.137:8084";//阿里云服务器IP
	//String SERVICE_IP = "www.ifarmcloud.com";
	//String SERVICE_IP = "192.168.1.58:8080";//服务器IP
	String SERVICE_IP = "115.233.231.94:7080";
	//String webViewUrl = "http://192.168.1.60:8080/ifm/login.htm?type=1";
	//String webViewUrl = "http://122.112.219.87/ifm/login.htm?type=5";
	String yzzsEwmUrl = "http://122.112.219.87:8089/download/YZZSewm.png";
	String haifmPEwmUrl = "http://122.112.219.87:8089/download/HAIFMewm.png";
	String type1 = "5";//纯数据录入
	String type2 = "4";//口袋牧场
	/**
	 * 如果报xmlPrase异常，那是ip可能多了/ifm
	 */

	/**发送延时**/
	//int FASONGYANSHI_50 = 50;//发送延时
	//int FASONGYANSHI_10 = 10;//发送延时
	int SEND_DELAY_PACK_INTERVAL = 10;//包之间间隔延时
	int SCJ_READ_TIMEOUT = 10000;//手持机展示间隔时间
	int SEND_CMD_TIMEOUT = 3000;//超时时间
	/**判断什么设备的命令**/
	int WHAT_DEV = 2;
	int packHeadH = 'h';//h
	int packHeadM = 'm';//m
	int packBottomE = 'e';//e
	int packBottomN = 'n';//n
	int packBottomD = 'd';//d
	int[] packHead = {'h', 'm'};
	int[] packBottom = {'e', 'n', 'd'};

	/** Sp 分隔符  */
	String SEPARSTOR = "#";
	/** 冒号  */
	String COLON = ":";
	/** what for Thread **/
	int WHAT_CHECK_VISION = 0;
	int SCJ_UPLOAD_VISION = 13;
	int WHAT_MCXX_SELECT = 10; // 得到牧场信息列表
	int WHAT_JQID_GET = 11; // 得到机器ID
	int WHAT_LYBM_UPLOAD = 12; // 蓝牙别名上传
	int WHAT_LOGIN_USER_CHECK_THREAD = 1;//登录
	int WHAT_LOGIN_GUEST_CHECK_THREAD = 2;//访客登陆
	int WHAT_REGISTER_THREAD = 3;//注册
	int WHAT_RESET_PASSWORD_THREAD = 4;//重置密码
	int WHAT_MODIFY_PASSWORD_THREAD = 5;//修改密码
	int WHAT_MODIFY_NICHENG_THREAD = 6;//修改昵称
	int WHAT_UPLOAD_TOUXIANG_THREAD = 7;//上传头像
	int WHAT_MODIFY_PHONE_NUMBER_THREAD = 8;//修改手机号

	int WHAT_SAVE_MCPZ_THREAD = 9;//保存牧场配置
	int WHAT_GET_MCPZ_THREAD = 10;//获取牧场配置
	/**连接服务器失败**/
	String UNCONNETCTION_SERVICE = "连接服务器失败，请检查网络";//连接服务器失败，请检查网络
	/**是否为游客标志，1为注册账户，2为游客**/
	String ZHUCE = "1";
	String YOUKE = "2";
	/**昵称长度**/
	int NNCD = 8;
	/**服务器头像文件夹**/
	String SERVICE_TOUXIANG_FILENAME = "/yhtx/";
	/**.jpg**/
	String JPG = ".jpg";
	String HTTP = "http://";
	/**个推**/
	String KG_ON = "1";
	String KG_OFF = "0";
	int KGZT_LENGTH = 15;
	
	String XX_XXTS_ALL = "HUIMV";//全体
	
	String XX_XXTS_QTTZ = "QTTZ"; //群体体重
	String XX_XXTS_TZBH = "TZBH"; //体重变化
	String XX_XXTS_TZBHQS = "TZBHQS"; //体重变化趋势
	String XX_XXTS_CLTZ = "CLTZ"; //出栏体重
	
	String XX_XXTS_QTRPJFW = "QTRPJFW"; //群体日平均访问
	String XX_XXTS_QTRPJFWBH = "QTRPJFWBH"; //群体日平均访问变化
	String XX_XXTS_SFW = "SFW"; //时访问
	
	String XX_XXTS_QCLRB = "QCLRB"; //全程料肉比
	String XX_XXTS_QCLRBBH = "QCLRBBH"; //全程料肉比变化
	
	String XX_XXTS_RSL = "RSL"; //日饲料
	String XX_XXTS_RSLBH = "RSLBH"; //日饲料变化
	
	String XX_XXTS_SWD = "SWD"; //时温度
	String XX_XXTS_WLGZ = "WLGZ"; //网络故障
	String XX_XXTS_GZH = "GZH"; //故障号（设备故障）
	String XX_XXTS_SJH = "SJH"; //事件号
}