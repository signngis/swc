package com.atguigu.restapi.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.bean.TMember;
import com.atguigu.restapi.bean.AppResult;
import com.atguigu.service.SystemService;
import com.atguigu.util.CacheUtils;
import com.atguigu.util.EmailUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="系统模块")
@RestController
@RequestMapping("/sys")
public class SystemController {
	
	@Autowired
	SystemService systemService;
	
	

	@ApiOperation("文件上传")
	@PostMapping("/upload")
	public AppResult<String> uploadFile(@RequestParam("file")MultipartFile file,
			HttpSession session) throws IOException {
		
		//1、将文件保存到服务器
		//  1）、把文件专门保存到专业的文件服务器；
		//	2）、把文件保存在当前项目下；  当前项目下/images/xxx.jpg
		
		InputStream is = file.getInputStream();
		//ServletContext；Application
		ServletContext servletContext = session.getServletContext();
		//获取到当前项目下images文件夹的真实路径（他在服务器里面的真实磁盘路径）
		//C:\Users\lfy\0228ee\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\rest-api\images
		String realPath = servletContext.getRealPath("/images");
		//防止文件名重复
		String filename =UUID.randomUUID().toString().replaceAll("-", "") + "_" + file.getOriginalFilename();
		FileOutputStream os = new FileOutputStream(realPath+"\\"+filename);
		IOUtils.copy(is, os);
		os.close();
		is.close();
		
		//2、返回文件的访问地址   http://xxx:8083/rest-api/images/filename
		String path = "/images/"+filename;
		return AppResult.success(path);
	}
	
}
