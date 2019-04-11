package com.atguigu.enume;

public enum AcctTypeEnum {
	
	//商业公司	个体工商户	个人经营	政府及非营利组织
	COMPANY("1","商业公司"),PERSON_COMPANY("2","个体工商户"),
	PERSONAL("3","个人经营"),GOV("4","政府及非营利组织");
	
	private String code;
	private String msg;
	
	
	private AcctTypeEnum(String code, String msg) {
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
