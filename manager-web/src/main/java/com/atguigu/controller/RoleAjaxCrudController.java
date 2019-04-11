package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.TRole;
import com.atguigu.constant.AppConstant;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/role")
// @Controller
// @ResponseBody
@RestController
public class RoleAjaxCrudController {

	@Autowired
	RoleService roleService;

	// 如果多个id，用,分隔
	@GetMapping("/delete")
	public String deleteRole(@RequestParam("ids") String ids) {
		//int i = 10/0;
		// id集合不为空进行删除
		if (!StringUtils.isEmpty(ids)) {
			List<Integer> idArray = new ArrayList<>();
			String[] strings = ids.split(",");
			for (String string : strings) {
				int id;
				try {
					id = Integer.parseInt(string);
					idArray.add(id);
				} catch (NumberFormatException e) {
				}
			}
			roleService.batchDelete(idArray);
		}

		return "ok";
	}

	// 修改Role
	@PostMapping("/update")
	public String updateRole(TRole role) {
		roleService.updateRole(role);
		return "ok";
	}

	// 新增Role
	@PostMapping("/save")
	public String saveRole(TRole role) {
		roleService.saveRole(role);
		return "ok";
	}

	@GetMapping("/getById")
	public TRole getById(@RequestParam("id") Integer rid) {

		return roleService.getRoleById(rid);
	}

	/**
	 * 1、开启SpringMVC高级模式 mvc:annotation-driven 
	 * 2、导入 jackson 相关依赖 
	 * <dependency>
	 * 		<groupId>com.fasterxml.jackson.core</groupId>
	 * 		<artifactId>jackson-core</artifactId> 
	 * </dependency> 
	 * <dependency>
	 * 		<groupId>com.fasterxml.jackson.core</groupId>
	 * 		<artifactId>jackson-databind</artifactId> 
	 * </dependency>
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	@GetMapping("/list.json")
	public PageInfo<TRole> allRoles(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@RequestParam(value = "ps", defaultValue = "5") Integer ps,
			@RequestParam(value = "condition", required = false) String condition) throws InterruptedException {
		// session.setAttribute(AppConstant.QUERY_CONDITION, condition);
		// session.setAttribute(AppConstant.PAGE_NUM, pn);
		//Thread.sleep(3000);
		PageHelper.startPage(pn, ps);
		List<TRole> roles = roleService.listAllRoles(condition);

		// 将查出的数据以及分页信息全部返回
		PageInfo<TRole> info = new PageInfo<>(roles, 3);
		return info;
	}

}
