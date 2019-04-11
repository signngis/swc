package com.atguigu.enume;

import java.util.Enumeration;

/**
 * 项目状态
 * @author lfy
 *
 */
public enum ProjectQueryEnum  {
	
	//项目其他状态
	//每天都有人去支持项目，一但项目到了结束期(35)，而且支持金额也OK【定时任务】
	WILLBEGIN("2","即将开始"),UP("U","众筹中"),SUCCESS("S","众筹成功");
	
	
	
	//如果要搜全网：  3和7状态，如果要搜分类热门是4和7状态
	private String code;
	private String msg;
	
	
	private ProjectQueryEnum(String code, String msg) {
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
