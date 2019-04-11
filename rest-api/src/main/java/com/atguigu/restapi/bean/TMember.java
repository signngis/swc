package com.atguigu.restapi.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TMember {
	
	@ApiModelProperty(required=false,value="用户id",hidden=true)
    private Integer id;

	@ApiModelProperty(required=true,value="用户账号")
    private String loginacct;

	@ApiModelProperty(required=true,value="用户密码")
    private String userpswd;

	@ApiModelProperty(required=false,value="用户昵称",hidden=true)
    private String username;

	@ApiModelProperty(required=true,value="邮箱")
    private String email;

	@ApiModelProperty(required=false,value="认证状态",hidden=true)
    private String authstatus;

	@ApiModelProperty(required=false,value="用户类型",hidden=true)
    private String usertype;

	@ApiModelProperty(required=false,value="真实姓名",hidden=true)
    private String realname;

	@ApiModelProperty(required=false,value="身份证号",hidden=true)
    private String cardnum;

	@ApiModelProperty(required=false,value="账户类型",hidden=true)
    private String accttype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct == null ? null : loginacct.trim();
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd == null ? null : userpswd.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAuthstatus() {
        return authstatus;
    }

    public void setAuthstatus(String authstatus) {
        this.authstatus = authstatus == null ? null : authstatus.trim();
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }

    public String getAccttype() {
        return accttype;
    }

    public void setAccttype(String accttype) {
        this.accttype = accttype == null ? null : accttype.trim();
    }
}