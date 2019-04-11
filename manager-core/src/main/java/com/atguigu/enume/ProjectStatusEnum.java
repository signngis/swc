package com.atguigu.enume;

import java.util.Enumeration;

/**
 * 项目状态
 * @author lfy
 *
 */
public enum ProjectStatusEnum  {
	
	//  项目状态合并
	CREATE("0","新建"),AUTHING("1","审核中"),AUTHED("2","审核成功"),
	HOT("3","全网热点推荐"),GROUPHOT("4","分类热点推荐"),ALLHOTS("7","全网&分类热门"),END("D","下架");
	
	
	
	
	
	//如果要搜全网：  3和7状态，如果要搜分类热门是4和7状态
	private String code;
	private String msg;
	
	
	private ProjectStatusEnum(String code, String msg) {
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
