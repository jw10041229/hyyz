package com.huimv.szmc.client;

public interface WsCmd {
	
	public static final String HAIFM_CZYH_REGISTER_INSERT = "HAIFM_CZYH_INSERT";// 注册
	public static final String HAIFM_YHM_CHECK = "HAIFM_YHM_CHECK";// 账户查重
	public static final String HAIFM_YHNC_CHECK = "HAIFM_YHNC_CHECK";// 昵称查重
	public static final String HAIFM_LXDH_CHECK = "HAIFM_LXDH_CHECK";// 手机号查重
	public static final String HAIFM_YHMM_RESET = "HAIFM_YHMM_RESET";// 重置密码
	public static final String HAIFM_YHMM_UPDATE = "HAIFM_YHMM_UPDATE";// 修改密码
	public static final String HAIFM_YHNC_UPDATE = "HAIFM_YHNC_UPDATE";// 修改昵称
	public static final String HAIFM_LOGIN_USER = "HAIFM_LOGIN_USER";// 登陆验证
	public static final String HAIFM_LOGIN_GUEST = "HAIFM_LOGIN_GUEST";// 访客登陆验证
	public static final String HAIFM_YHTX_UPLOAD = "HAIFM_YHTX_UPLOAD";// 上传头像
	
	public static final String HAIFMP_VISION_UPGRADE = "HAIFMP_VISION_UPGRADE";// 版本升级
	public static final String HAIFM_VISION_UPGRADE = "HAIFM_VISION_UPGRADE";// 版本升级

	public static final String HAIFM_SAVE_MCPZ = "HAIFM_SAVE_MCPZ";// 保存牧场配置
	public static final String HAIFM_GET_MCPZ = "HAIFM_GET_MCPZ";// 获取牧场配置
	// webservice 返回状态定义，跟服务器端保持一致
	public static final int SUCCESS = 0;
	public static final int SYSTEM_ERROR = -100;
	public static final int SYSTEM_OTHER_ERROR = -101;
	public static final int SYSTEM_UPGRADE = 1;
	public static final int PARAM_ERROR = -1;
	public static final int REC_NUM_ERROR = -2;
	public static final int BUSINESS_3 = -3;
	public static final int BUSINESS_4 = -4;
	public static final int BUSINESS_5 = -5;
	public static final int BUSINESS_6 = -6;
	public static final int BUSINESS_7 = -7;
	public static final int BUSINESS_8 = -8;
	public static final int BUSINESS_9 = -9;
	public static final int BUSINESS_10 = -10;
	public static final int  AD_BBXX_GX = 1004;
	public static final int  AD_BBXX_WGX = 1005;
}