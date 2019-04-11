package com.atguigu.front.bean;

public class AppResult {

	private Integer code;// 处理状态 0：成功 1：失败
	private String msg;// 提示
	private Object content;

	// 快速响应成功
	public static AppResult success(Object content) {
		AppResult result = new AppResult();
		result.setCode(0);
		result.setMsg("操作成功");
		result.setContent(content);
		return result;
	}

	public static AppResult success(Integer code, String msg, Object content) {
		AppResult result = new AppResult();
		result.setCode(code);
		result.setMsg(msg);
		result.setContent(content);
		return result;
	}

	// 快速失败
	public static AppResult fail(String msg) {
		AppResult result = new AppResult();
		result.setCode(1);
		result.setMsg(msg);
		return result;
	}

	// 快速失败
	public static AppResult fail(Integer code, String msg, Object content) {
		AppResult result = new AppResult();
		result.setCode(code);
		result.setMsg(msg);
		result.setContent(content);
		return result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
