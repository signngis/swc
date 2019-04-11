package com.atguigu.restapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.TCert;
import com.atguigu.bean.TMember;
import com.atguigu.bean.TMemberCert;
import com.atguigu.bean.TMemberProcinst;
import com.atguigu.restapi.bean.AppResult;
import com.atguigu.restapi.vo.MemberCertsVo;
import com.atguigu.service.CertService;
import com.atguigu.service.MemberService;
import com.atguigu.service.SystemService;
import com.atguigu.util.CacheUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="会员中心模块")
@RequestMapping("/member/center")
@RestController
public class MemberCenterController {
	
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CertService certService;
	
	
	
	@Autowired
	SystemService systemService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	RepositoryService repositoryService;
	
	@ApiOperation(value = "实名审核：step00-保存会员账号类型")
	@PostMapping("/accttype")
	public AppResult baseInfoSave(
			@ApiParam("账号类型")
			@RequestParam("accttype")String accttype,
			@ApiParam("身份令牌")
			@RequestParam("token")String token) {
		
		CacheUtils.put("auth:step:"+token, 0);
		//通过令牌获取用户
		TMember loginMember = (TMember) CacheUtils.get(token);
		
		TMember member = new TMember();
		member.setId(loginMember.getId());
		member.setAccttype(accttype);
		
		//更新会员账号
		memberService.updateMember(member);
		
		
		return AppResult.success(null);
	}
	
	@ApiOperation(value = "实名审核：step01-保存会员基本信息")
	@PostMapping("/baseinfo")
	public AppResult baseInfoSave(
			@ApiParam("真实姓名")
			@RequestParam("realname")String realname,
			@ApiParam("身份证号")
			@RequestParam("idcard")String idcard,
			@ApiParam("身份令牌")
			@RequestParam("token")String token) {
		//前一步
		Integer  i = (Integer) CacheUtils.get("auth:step:"+token);
		if(i==null || i != 0) {
			return AppResult.fail("操作异常，请按照顺序");
		}
		
		CacheUtils.put("auth:step:"+token, 1);
		//通过令牌获取用户
		TMember loginMember = (TMember) CacheUtils.get(token);
		
		TMember member = new TMember();
		member.setRealname(realname);
		member.setCardnum(idcard);
		member.setId(loginMember.getId());
		memberService.updateMember(member);
		
		
		return AppResult.success(null);
	}
	
	
	@ApiOperation("返回当前用户账户类型应该对应提交的资质文件")
	@GetMapping("/certs")
	public AppResult<List<TCert>> acctTypeCert(
			@ApiParam("用户令牌")
			@RequestParam("token")String token) {
		//通过令牌获取用户
		TMember loginMember = (TMember) CacheUtils.get(token);
		
		List<TCert> certs = certService.listCertsByAcctType(loginMember.getAccttype());
		return AppResult.success(certs);
	}
	
	//  url?key=value
	//  post：请求体中可以携带任意数据（文件）；将页面的数据变为json的格式
	//   [{token:'aa',certid:'214234',iconpath:'dadada'},{token:'aa',certid:'214234',iconpath:'dadada'}]
	//  SpringMVC把对象能变为json写出去，把json变为对象拿回来？
	// @ResponseBody:把对象写出去（转为json或者其他格式）
	// @RequestBody:把请求体中的数据逆向回来；
	// 以后用请求体可以传输复杂对象（对象序列化以后）【序列化成json】；SpringMVC可以通过@RequestBody将请求体中的json数据获取来反序列化为对象
	@ApiOperation("实名审核：step02-保存当前用户提交的所有资质")
	@PostMapping("/certs")
	public AppResult<String> saveCerts(@RequestBody List<MemberCertsVo> vos) {
		//前一步
		Integer  i = (Integer) CacheUtils.get("auth:step:"+vos.get(0).getToken());
		if(i==null || i != 1) {
			return AppResult.fail("操作异常，请按照顺序");
		}
		
		CacheUtils.put("auth:step:"+vos.get(0).getToken(), 2);
		TMember login = (TMember) CacheUtils.get(vos.get(0).getToken());
		
		List<TMemberCert> certs = new ArrayList<>();
		for (MemberCertsVo vo : vos) {
			TMemberCert memberCert = new TMemberCert();
			BeanUtils.copyProperties(vo, memberCert);
			//用户id
			memberCert.setMemberid(login.getId());
			certs.add(memberCert);
		}
		
		
		certService.saveMemberCerts(certs);
		
		return AppResult.success(null);
	}
	
	
	
	@ApiOperation("实名审核：step03-给用户发送邮箱验证码")
	@GetMapping("/code")
	public AppResult sendEmailCode(String token) throws EmailException {
		//前一步
		Integer  i = (Integer) CacheUtils.get("auth:step:"+token);
		if(i==null || i != 2) {
			return AppResult.fail("操作异常，请按照顺序");
		}
		
		CacheUtils.put("auth:step:"+token, 3);
		
		
		TMember member = (TMember) CacheUtils.get(token);
		String code = UUID.randomUUID().toString().substring(0, 5);
		String content = "<h1>你好，"+member.getLoginacct()+"</h1>"+
				"<h2>你的验证码是：</h2><h6>"+code+"</h6>";
		
		CacheUtils.put("code:"+token, code);
		systemService.sendEmail(member.getEmail(), content, "尚筹网-验证码");
		return AppResult.success(null);
	}
	
	@ApiOperation("实名审核：step04-校验验证码")
	@GetMapping("/validate/code")
	public AppResult<String> validateCode(String token,String code){
		//前一步
		Integer  i = (Integer) CacheUtils.get("auth:step:"+token);
		if(i==null || i != 3) {
			return AppResult.fail("操作异常，请按照顺序");
		}
		
		
		
		String userCode = (String) CacheUtils.get("code:"+token);
		if(code.equals(userCode)) {
			CacheUtils.put("auth:step:"+token, 4);
			CacheUtils.remove("code:"+token);
			
			//启动实名审核流程；
			//run
			//工单（启动的流程的实例id）：和当前用户关联起来。
			//runtimeService.startProcessInstanceById("")
			//0、获取当前用户信息
			TMember member = (TMember) CacheUtils.get(token);
			Map<String, Object>  map = new HashMap<>();
			map.put("userEmail", member.getEmail());
			map.put("username", member.getLoginacct());
			map.put("id", member.getId());
			
			//1、查询实名认证的最新流程
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("realauth")
				.latestVersion()
				.singleResult();
			
			//启动，流程
			ProcessInstance processInstance = 
					runtimeService.startProcessInstanceById(processDefinition.getId(),map);
			
			//2、保存流程实例和用户对应的信息
			
			TMemberProcinst procinst = new TMemberProcinst();
			procinst.setMemberid(member.getId());
			procinst.setProcinstid(processInstance.getId());
			//建议类型为枚举
			procinst.setProctype("realauth");
			memberService.saveMemberProcess(procinst);
			CacheUtils.remove("auth:step:"+token);
			return AppResult.success(null);
		}
		
		return AppResult.fail("验证码错误");
	}
	

}
