package com.atguigu.restapi.bean;

public class AppResult<T> {

	private Integer code;// 处理状态 0：成功 1：失败
	private String msg;// 提示
	private T content;//携带的数据是泛型的

	// 快速响应成功
	public static<T> AppResult<T> success(T content) {
		AppResult<T> result = new AppResult<T>();
		result.setCode(0);
		result.setMsg("操作成功");
		result.setContent(content);
		return result;
	}

	public static<T> AppResult<T> success(Integer code, String msg, T content) {
		AppResult<T> result = new AppResult<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setContent(content);
		return result;
	}

	// 快速失败
	public static<T> AppResult<T> fail(String msg) {
		AppResult<T> result = new AppResult<T>();
		result.setCode(1);
		result.setMsg(msg);
		return result;
	}

	// 快速失败
	public static<T> AppResult<T> fail(Integer code, String msg, T content) {
		AppResult<T> result = new AppResult<T>();
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

	public void setContent(T content) {
		this.content = content;
	}

}
