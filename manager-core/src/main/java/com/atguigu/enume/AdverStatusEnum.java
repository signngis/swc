package com.atguigu.enume;

/**
 * 广告状态
 * @author lfy
 *
 */
public enum AdverStatusEnum {
	
	CREATE("0","新建"),AUTHING("1","审核中"),AUTHED("2","审核通过"),UP("3","上架"),DOWN("4","下架");
	
	private String code;
	private String msg;
	
	private AdverStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
