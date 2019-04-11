package com.atguigu.restapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MemberCertsVo {
	
	@ApiModelProperty("用户令牌")
	private String token;
	
	@ApiModelProperty("资质id")
	private Integer certid;
	
	@ApiModelProperty("资质图片路径")
	private String iconpath;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getCertid() {
		return certid;
	}
	public void setCertid(Integer certid) {
		this.certid = certid;
	}
	public String getIconpath() {
		return iconpath;
	}
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}
	
	

}
