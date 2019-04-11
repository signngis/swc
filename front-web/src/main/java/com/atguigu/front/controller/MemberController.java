package com.atguigu.front.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.atguigu.front.bean.AppResult;

@RequestMapping("/member")
@Controller
public class MemberController {
	@Value("${rest.baseurl}")
	private String baseUrl;
	
	RestTemplate restTemplate = new RestTemplate();
	
	//2、密码重置
	@GetMapping("/reset")
	public String resetPwd(@RequestParam("pwd")String pwd,
			@RequestParam("token")String token,Model model) {
		String url = baseUrl+"member/reset?token="+token+"&pwd="+pwd;
		AppResult result = restTemplate.getForObject(url, AppResult.class);
		if(result.getCode() == 0) {
			//成功
			model.addAttribute("successMsg", "密码重置完成 <a href='/front-web/login.html'>再去登陆</a>");
			return "success";
		}else {
			model.addAttribute("successMsg", "网络异常，请再试一次");
			return "success";
		}
	}
	
	//1、发送密码重置邮件
	@RequestMapping("/sendEmail")
	public String sendEmail(@RequestParam("email")String email,
			Model model) {
		
		
		//2、邮件发送是rest-api的功能
		String url = baseUrl+"member/forget?email="+email;
		
		//3、可以发送HTTP的工具；HttpClient、RestTemplate；
		Map map = null;
		try {
			map = restTemplate.getForObject(url, Map.class);
		} catch (Exception e) {
			model.addAttribute("msg", "网络异常");
			return "forward:/user/forget";
		}
		//4、响应内容
		if((Integer)map.get("code") == 0) {
			//响应成功
			model.addAttribute("successMsg", "邮件发送成功，请登陆你的邮箱");
			return "success";
		}else {
			//邮件发送失败；
			model.addAttribute("msg", "网络异常");
			return "forward:/user/forget";
		}
		
		
	}
}
