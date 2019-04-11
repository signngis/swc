package com.atguigu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.bean.TUser;
import com.atguigu.constant.AppConstant;
import com.atguigu.vo.ProcessDefintionVO;

@Controller
@RequestMapping("/proc")
public class ProcessController {
	
	@Autowired
	RepositoryService repositoryService;
	
	//返回流程图
	@GetMapping("/images")
	public void procImg(@RequestParam("pid")String procDefId,
			HttpServletResponse response) throws IOException {
		
		InputStream is = repositoryService.getProcessDiagram(procDefId);
		
		ServletOutputStream os = response.getOutputStream();
		
		IOUtils.copy(is, os);
		
		os.close();
		is.close();
		
	}
	
	//查询所有流程定义
	@ResponseBody
	@GetMapping("/all.json")
	public List<ProcessDefintionVO> queryAllProcess() {
		List<ProcessDefintionVO> defintionVOs = new ArrayList<ProcessDefintionVO>();
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition processDefinition : list) {
			ProcessDefintionVO vo = new ProcessDefintionVO();
			BeanUtils.copyProperties(processDefinition, vo);
			
			defintionVOs.add(vo);
		}
		//VO：
		return defintionVOs;
	}
	
	
	/**
	 * 文件上传的步骤:
	 * 1、配置文件上传解析器
	 * 		<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
				<property name="defaultEncoding" value="UTF-8"></property>
				<property name="maxUploadSize" value="#{1024*1024*200}"></property>
			</bean>
	 * 
	 * 2、只需要在方法参数位置写一个 MultipartFile
	 * 3、准备一个上传页面
	 * 	<form action="xxx" method="post" enctype="multipart/form-data">
	 * 			<input type="file" name="photo">
	 * 			<input type="text" name="username">
	 * 	</form>
	 * 4、处理请求的方法签名
	 * 		public String deploy(@RequestParam("photo")MultipartFile file,@RequestParam("username")String  name) {
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@PostMapping("/deploy")
	public Map<String, Object> deploy(@RequestParam("file")MultipartFile file,HttpSession session) throws IOException {
		/*repositoryService.createDeployment()
			.addInputStream(resourceName, inputStream)
			.d*/
		//原始的文件名
		String filename = file.getOriginalFilename();
		//文件的流
		InputStream is = file.getInputStream();
		
		TUser user = (TUser) session.getAttribute(AppConstant.LOGIN_USER);
		Deployment deploy = repositoryService.createDeployment()
				.addInputStream(filename, is)
				.name(user.getLoginacct())
				.deploy();
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "部署成功");
		map.put("content", deploy);
		return map;
	}

}
