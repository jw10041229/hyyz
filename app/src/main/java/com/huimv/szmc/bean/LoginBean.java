package com.huimv.szmc.bean;

public class LoginBean {
    private String yhm;//用户名
    private String yhmm;//用户密码
    private String yhlx;//用户类型
    private String cid;//推送cid
    private String sbid;//唯一标识符

	public String getSbid() {
		return sbid;
	}
	public void setSbid(String sbid) {
		this.sbid = sbid;
	}
	public String getYhm() {
        return yhm;
    }
    public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public void setYhm(String yhm) {
        this.yhm = yhm;
    }
    public String getYhmm() {
        return yhmm;
    }
    public void setYhmm(String yhmm) {
        this.yhmm = yhmm;
    }
    public String getYhlx() {
        return yhlx;
    }
    public void setYhlx(String yhlx) {
        this.yhlx = yhlx;
    }
}
