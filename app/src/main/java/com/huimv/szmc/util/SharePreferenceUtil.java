package com.huimv.szmc.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	public static final String MESSAGE_HAIFM_KEY = "SPmessage";
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setAccount(String account) {
		editor.putString("account", account);
		editor.commit();
	}

	public String getIsLogined() {//1是已经登陆过，0是没有登陆过
		return sp.getString("IsLogined", "0");
	}
	public void setIsLogined(String IsLogined) {
		editor.putString("IsLogined", IsLogined);
		editor.commit();
	}

	public String getAccount() {
		return sp.getString("account", "");
	}
    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }
    
    public String getPassword() {
        return sp.getString("password", "");
    }
    
    public void setNicheng(String nicheng) {
        editor.putString("nicheng", nicheng);
        editor.commit();
    }
    
    public String getNicheng() {
        return sp.getString("nicheng", "");
    }
    public void setUUId(String uuid) {
        editor.putString("uuid", uuid);
        editor.commit();
    }
    
    public String getUUId() {
        return sp.getString("uuid", "");
    }
    
    public void setYoukeTag(String tag) {
        editor.putString("YoukeTag", tag);
        editor.commit();
    }
    
    public String getYoukeTag() {
        return sp.getString("YoukeTag", "1");//1为注册账户，2为游客
    }
    
    public void setShoujihao(String shoujihao) {
        editor.putString("shoujihao", shoujihao);
        editor.commit();
    }
    
    public String getShoujihao() {
        return sp.getString("shoujihao", "");
    }
    
    public void setUserID(String userID) {
        editor.putString("userID", userID);
        editor.commit();
    }
    
    public String getUserID() {
        return sp.getString("userID", "");
    }
    
    public void setHeadPicUrl(String url) {
        editor.putString("headPicUrl", url);
        editor.commit();
    }
    
    public String getHeadPicUrl() {
        return sp.getString("headPicUrl", "");
    }
    
    public void setCid(String cid) {
        editor.putString("cid", cid);
        editor.commit();
    }
    
    public String getCid() {
        return sp.getString("cid", "");
    }
    
    public void setKgzt(String kgzt) {
        editor.putString("kgzt", kgzt);
        editor.commit();
    }
    
    public String getKgzt() {
        return sp.getString("kgzt", "111111111111111");
    }
    
	public void setXxid(String xxid) {
		editor.putString("xxid", xxid);
		editor.commit();
	}

	public String getXxid() {
		return sp.getString("xxid", "");
	}
	
	public void setUniqueID(String UniqueID) {
		editor.putString("UniqueID", UniqueID);
		editor.commit();
	}

	public String getUniqueID() {
		return sp.getString("UniqueID", "");
	}
    /**
     * 版本信息
     */
    public void setVersion(String version) {
        editor.putString("version", version);
        editor.commit();
    }

    public String getVersion() {
        return sp.getString("version", "");
    }

    /**
     * 测试IP
     */
    public void setTestIP(String ip) {
        editor.putString("ip", ip);
        editor.commit();
    }

    public String getTestIP() {
        return sp.getString("ip", "");
    }
}
