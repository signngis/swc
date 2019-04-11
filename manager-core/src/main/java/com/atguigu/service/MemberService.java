package com.atguigu.service;

import org.apache.commons.mail.EmailException;

import com.atguigu.bean.TMember;
import com.atguigu.bean.TMemberProcinst;
import com.atguigu.enume.AuthStatusEnum;

/**
 * 会员服务
 * @author lfy
 *
 */
public interface MemberService {
	
	public void sendForgetPwdEmail(String email)  throws EmailException;

	public TMember getMemberByEmail(String email);

	public void resetPwd(String token, String pwd);

	//注册
	public void regist(String loginacct, String email, String pwd);
	public void checkMemberExist(String loginacct, String email);

	//登陆
	public TMember login(String loginacct, String pwd);
	
	//更新用户的实名状态
	public void updateAuthStatus(Integer id,AuthStatusEnum authStatusEnum);

	public void updateMember(TMember member);

	//保存用户和其启动的流程实例
	public void saveMemberProcess(TMemberProcinst procinst);


}
