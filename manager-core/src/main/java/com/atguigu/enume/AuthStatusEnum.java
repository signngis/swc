package com.atguigu.enume;

public enum AuthStatusEnum {
	
	UNAUTH("0","未认证"),AUTHING("1","认证中"),AUTHED("2","认证通过");
	
	private String code;
	private String msg;
	
	private AuthStatusEnum(String code, String msg) {
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
