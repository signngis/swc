package com.atguigu.enume;

/**
 * 项目回报
 * @author lfy
 *
 */
public enum ProjectRetEnum {
	
	GOODS("1","实物回报"),VIRTUAL("2","虚拟物品");
	
	private String code;
	private String msg;
	
	private ProjectRetEnum(String code, String msg) {
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
