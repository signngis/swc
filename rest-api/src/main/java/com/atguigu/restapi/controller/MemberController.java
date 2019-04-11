package com.atguigu.restapi.controller;

import java.util.UUID;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.restapi.bean.TMember;
import com.atguigu.restapi.vo.MemberAppVO;
import com.atguigu.restapi.bean.AppResult;
import com.atguigu.service.MemberService;
import com.atguigu.service.SystemService;
import com.atguigu.util.CacheUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("用户模块")
@RequestMapping("/member")
@RestController
public class MemberController {
	
	
	@Autowired
	SystemService systemService;
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 领域对象模型：
	 * JavaBean：
	 *  DAO：（Data Access Object）：访问数据库的对象；
	 *  DO：（Database Object）：专门和数据库表记录关联的对象；
	 *  DTO/TO：（Data Transport Object）：数据传输对象；【封装数据进行上下层传递】
	 *  （Transport Object）
	 *  VO：（View Object/Value Object）：视图/值对象；【封装/显示页面数据】
	 *  POJO：普通的java对象
	 *  
	 *  DO是全字段：页面只提交部分字段；VO：（专门收集页面数据）；数据库需要这个对象就可以将VO转为DO；
	 *  
	 *  VO（封装页面数据）===DTO（各层之间数据传输）=====DO（TMember 专门和数据库表进行映射的对象）
	 *  
	 *  安全性：敏感字段，虽然DO里面封装了，但是交给页面的时候这个数据不能显示；
	 *  	MemberVO{						
	 *  		private String loginacct;
	 *  		private String email;
	 *  	}
	 *  
	 *  	TMember{
	 *  		xxxx
	 *  	}
	 *  什么时候用到VO（Object）；
	 *  	1、如果字段比较多（5个及以上）；
	 *  		1）、字段比较多，临时数据；Map
	 *  		2）、字段比较多，模式都一样；Class
	 *  	2、如果字段比较少（5）；
	 *  		使用参数列表即可
	 *  
	 *  
	 *  登陆、注册问题：
	 *  	1）、TMember字段太多；页面只提交部分；
	 *  	2）、TMember使用SwaggerAPI过滤部分字段；
	 *  	3）、不同业务逻辑又没办法共用；
	 *  解决办法：
	 *  	1）、使用VO、TO封装或者传输数据；
	 *  
	 */
	
	
	
	
	
	/**
	 * @ApiParam("账号")String loginacct,
			@ApiParam("密码")String pwd,
			@ApiParam("邮箱")String email
	 * @return
	 */
	@ApiOperation("用户注册")
	@PostMapping("/regist")
	public AppResult regist(
			@ApiParam(value="登陆账号",required=true)
			@RequestParam("loginacct")String loginacct,
			
			@ApiParam("邮箱")
			@RequestParam("email")String email,
			
			@ApiParam("密码")
			@RequestParam("pwd")String pwd) {
		
		
		
		try {
			memberService.regist(loginacct,email,pwd);
		} catch (Exception e) {
			return AppResult.fail(e.getMessage());
		}
		
		return AppResult.success(null);
	}
	
	@ApiOperation("用户登陆")
	@PostMapping("/login")
	public AppResult<MemberAppVO> login(
			@ApiParam("登陆账号")
			@RequestParam("loginacct")String loginacct,
			
			@ApiParam("密码")
			@RequestParam("pwd")String pwd) {
		//1、rest-api向数据库进行登陆；
		//2、登陆成功返回TMember对象；
		//3、将登陆成功的用户交给前端；
		//4、以后前端和用户有关的所有请求，一定要让我们知道这是哪个用户；（携带用户id？）
		//5、用户登陆成功以后生成唯一一个授权码，这个授权码代表当前用户，用户发的每一个请求必须携带授权码；
		//6、授权码==用户？   后台在统一的缓存里面保存 授权码和用户的关系；
		
		//给前端返回什么数据；1、用户的部分字段信息   2、用户的授权码
		
		com.atguigu.bean.TMember member = memberService.login(loginacct,pwd);
		if(member!=null) {
			String token = UUID.randomUUID().toString().replaceAll("-", "");
			//存储授权码与用户的对应关系
			CacheUtils.put(token, member);
			MemberAppVO vo = new MemberAppVO();
			//赋值属性，传递
			BeanUtils.copyProperties(member, vo);
			vo.setToken(token);
			return AppResult.success(vo);
		}
		
		return AppResult.fail("用户名密码错误");
	}
	
	
	/**
	 * 发送密码重置邮件
	 * @return
	 * @throws EmailException 
	 */
	
	@ApiOperation("忘记密码-发送密码重置邮件")
	@GetMapping("/forget")
	public AppResult sendEmail(@RequestParam("email")String email) throws EmailException {
		
		//1、发送密码重置邮件
		memberService.sendForgetPwdEmail(email);
		
		return AppResult.success(null);
	}
	
	@ApiOperation("忘记密码-重置密码")
	@GetMapping("/reset")
	public AppResult resetPwd(String token,String pwd) {
		
		try {
			//重置密码
			memberService.resetPwd(token, pwd);
			return AppResult.success(null);
		} catch (Exception e) {
			return AppResult.fail(e.getMessage());
		}
		
	}

}
