package com.atguigu.enume;

//排序规则
public enum OrderByEnum {
	
	NEWUP("1","最新上线"),MONEYMUCH("2","金额最多"),SUPPORTMANY("3","支持最多");
	
	private String code;
	private String msg;
	private OrderByEnum(String code, String msg) {
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
