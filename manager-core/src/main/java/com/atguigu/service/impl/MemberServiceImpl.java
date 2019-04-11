package com.atguigu.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.atguigu.bean.TMember;
import com.atguigu.bean.TMemberExample;
import com.atguigu.bean.TMemberProcinst;
import com.atguigu.enume.AuthStatusEnum;
import com.atguigu.exception.TokenInvaildException;
import com.atguigu.exception.UserEmailExistException;
import com.atguigu.exception.UserLoginAcctExistException;
import com.atguigu.mapper.TMemberMapper;
import com.atguigu.mapper.TMemberProcinstMapper;
import com.atguigu.service.MemberService;
import com.atguigu.service.SystemService;
import com.atguigu.util.CacheUtils;
import com.atguigu.util.MD5Utils;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	SystemService systemService;
	@Autowired
	MemberService memberService;

	@Autowired
	TMemberMapper memberMapper;
	
	@Autowired
	TMemberProcinstMapper memberProcinstMapper;

	/**
	 * <bean class="com.atguigu.service.impl.MemberServiceImpl">
	 * <property name="pwdPage" value="${member.pwd.page}"></property> </bean>
	 */
	@Value("${member.pwd.page}")
	private String pwdPage;

	// 发送密码重置邮件
	@Override
	public void sendForgetPwdEmail(String email) throws EmailException {
		// 1、生成用户的唯一token；
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		// 2、查出当前邮箱地址对应的用户
		TMember member = memberService.getMemberByEmail(email);

		// 3、存储当前令牌和当前会员的关系
		CacheUtils.put(token, member);

		String content = "<h1>你好，" + member.getLoginacct() + "，请点击下面链接重置密码</h1>" + "<a href='" + pwdPage + "?token="
				+ token + "'>密码重置</a>";

		// 4、发送密码重置邮件
		systemService.sendEmail(email, content, "尚筹网-密码重置");

	}

	@Override
	public TMember getMemberByEmail(String email) {
		// TODO Auto-generated method stub

		TMemberExample example = new TMemberExample();
		example.createCriteria().andEmailEqualTo(email);

		List<TMember> list = memberMapper.selectByExample(example);
		return list.size() != 1 ? null : list.get(0);
	}

	@Override
	public void resetPwd(String token, String pwd) {
		// TODO Auto-generated method stub
		// 1、根据令牌获取用户信息
		TMember member = (TMember) CacheUtils.get(token);
		if (member != null) {
			member.setUserpswd(MD5Utils.digestPassword(pwd));
			// 2、修改密码
			memberMapper.updateByPrimaryKeySelective(member);
			// 3、完成以后删除
			CacheUtils.remove(token);

		} else {
			throw new TokenInvaildException();
		}

	}

	@Override
	public void regist(String loginacct, String email, String pwd) {
		// TODO Auto-generated method stub
		//1、检查
		checkMemberExist(loginacct, email);
		//2、初始化用户信息并注册
		TMember member = new  TMember();
		member.setLoginacct(loginacct);
		member.setUserpswd(MD5Utils.digestPassword(pwd));
		member.setUsername(loginacct);
		member.setEmail(email);
		member.setAuthstatus(AuthStatusEnum.UNAUTH.getCode());
		member.setUsertype("1");
		
		memberMapper.insertSelective(member);
	}

	@Override
	public void checkMemberExist(String loginacct, String email) {
		// TODO Auto-generated method stub
		// 1、检查账号

		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		long l = memberMapper.countByExample(example);
		if (l > 0) {
			throw new UserLoginAcctExistException();
		}

		// 2、检查邮箱
		TMemberExample example1 = new TMemberExample();
		example1.createCriteria().andEmailEqualTo(email);
		long m = memberMapper.countByExample(example1);
		if (m > 0) {
			throw new UserEmailExistException();
		}
		
	}

	@Override
	public TMember login(String loginacct, String pwd) {
		// TODO Auto-generated method stub
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct).andUserpswdEqualTo(MD5Utils.digestPassword(pwd));
		
		List<TMember> list = memberMapper.selectByExample(example);
		return list.size()!=0?list.get(0):null;
	}

	@Override
	public void updateAuthStatus(Integer id, AuthStatusEnum authStatusEnum) {
		// TODO Auto-generated method stub
		TMember member = new TMember();
		member.setId(id);
		member.setAuthstatus(authStatusEnum.getCode());
		//更新实名状态
		memberMapper.updateByPrimaryKeySelective(member);
	}

	@Override
	public void updateMember(TMember member) {
		// TODO Auto-generated method stub
		memberMapper.updateByPrimaryKeySelective(member);
	}

	@Override
	public void saveMemberProcess(TMemberProcinst procinst) {
		//根据业务逻辑需要判断这个流程是否是可以同时存在多个
		
		memberProcinstMapper.insertSelective(procinst);
		
	}

}
