package com.model;

public class AppUpdate {
	
	boolean resCode;
	String msg;
	String version;
	String platform;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isResCode() {
		return resCode;
	}
	public void setResCode(boolean resCode) {
		this.resCode = resCode;
	}
	public String getMsg() {
		return msg;
	}
	
	

}
