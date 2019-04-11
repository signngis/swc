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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bean.TUser;
import com.atguigu.constant.AppConstant;
import com.atguigu.exception.UserEmailExistException;
import com.atguigu.exception.UserLoginAcctExistException;
import com.atguigu.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/user")
@Controller
public class UserCRUDController {

	@Autowired
	UserService userService;
	
	
	//如果多个id，用,分隔
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("ids")String ids,HttpSession session,Model model) {
		//来到列表页面
		Integer  pn = (Integer) session.getAttribute(AppConstant.PAGE_NUM);
		String condition = (String) session.getAttribute(AppConstant.QUERY_CONDITION);
		
		
		//id集合不为空进行删除
		if(!StringUtils.isEmpty(ids)) {
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
			userService.batchDelete(idArray);
		}
		
		model.addAttribute("pn", pn);
		model.addAttribute("condition", condition);
		return "redirect:/user/list.html";
	}
	
	
	//带上分页条件的更新
	//用户更新
	@PutMapping("/edit")
	public String updateUser(TUser user,HttpSession session,Model model) {
		
		try {
			userService.updateUser(user);
			Integer  pn = (Integer) session.getAttribute(AppConstant.PAGE_NUM);
			String condition = (String) session.getAttribute(AppConstant.QUERY_CONDITION);
			//中文的URL地址是需要编码的
			model.addAttribute("pn", pn);
			model.addAttribute("condition", condition);
			//重定向携带数据：
			//1、放在model中，数据会自己编码好放在url地址?后面（查询字符串）
			//2、放在RedirectAttributes中使用addFlashAttribute模拟给请求域中放数据用一次就结束
			return "redirect:/user/list.html";
		} catch (Exception e) {
			model.addAttribute("msg", "邮箱重复了");
			TUser user2 = userService.getUser(user.getId());
			user2.setEmail(user.getEmail());
			model.addAttribute("TUser", user2);
			return "auth/user-edit";
		}
		
	}
	
	//去修改页面
	@GetMapping("/edit.html")
	public String toEditPage(@RequestParam("id")Integer id,Model model) {
		
		TUser user = userService.getUser(id);
		model.addAttribute("TUser", user);
		return "auth/user-edit";
	}

	// post形式的 /user
	//发请求可以带上任意后缀名；
	// /user/add.html 去添加页面
	// /user/add 保存用户
	@PostMapping("/add")
	public String saveUser(TUser user,Model model) {
		try {
			boolean saveUser = userService.saveUser(user);
			return "redirect:/user/list.html?pn="+Integer.MAX_VALUE/2;
		} catch (UserLoginAcctExistException e) {
			model.addAttribute("msg", e.getMessage());
			//转发到页面回显
			return "auth/user-add";
		} catch (UserEmailExistException e) {
			model.addAttribute("msg", e.getMessage());
			//转发到页面回显
			return "auth/user-add";
		}
	}

	/**
	 * 查询所有用户
	 */
	@GetMapping("/list.html")
	public String list(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@RequestParam(value = "ps", defaultValue = "5") Integer ps,
			@RequestParam(value = "condition", required = false) String condition, HttpSession session) {
		// 查询所有用户
		PageHelper.startPage(pn, ps);
		session.setAttribute(AppConstant.QUERY_CONDITION, condition);
		session.setAttribute(AppConstant.PAGE_NUM, pn);
		List<TUser> users = userService.listAll(condition);

		PageInfo<TUser> info = new PageInfo<>(users, 5);

		model.addAttribute("info", info);
		return "auth/user";
	}

}
