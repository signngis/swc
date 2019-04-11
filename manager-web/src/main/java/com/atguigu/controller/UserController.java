package com.atguigu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.bean.TPermission;
import com.atguigu.bean.TRole;
import com.atguigu.bean.TUser;
import com.atguigu.constant.AppConstant;
import com.atguigu.exception.UserEmailExistException;
import com.atguigu.exception.UserLoginAcctExistException;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.atguigu.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	RoleService roleService;
	
	
	@ResponseBody
	@PostMapping("/deleterole")
	public String deleteRole(
			@RequestParam("rids")String rids,
			@RequestParam("uid")Integer uid,
			HttpSession session) {
		
		if(!StringUtils.isEmpty(rids)) {
			String[] split = rids.split(",");
			List<Integer> ridInts = new ArrayList<>();
			for (String i : split) {
				try {
					int parseInt = Integer.parseInt(i);
					ridInts.add(parseInt);
				} catch (NumberFormatException e) {
				}
			}
			//新增角色
			roleService.deleteUserRole(uid,ridInts);
		}
		
		session.setAttribute("msg", "用户角色删除成功");
		
		return "ok";
		
	}
	
	@ResponseBody
	@PostMapping("/addrole")
	public String addRole(
			@RequestParam("rids")String rids,
			@RequestParam("uid")Integer uid,
			HttpSession session) {
		
		if(!StringUtils.isEmpty(rids)) {
			String[] split = rids.split(",");
			List<Integer> ridInts = new ArrayList<>();
			for (String i : split) {
				try {
					int parseInt = Integer.parseInt(i);
					ridInts.add(parseInt);
				} catch (NumberFormatException e) {
				}
			}
			//新增角色
			roleService.insertUserRole(uid,ridInts);
		}
		
		session.setAttribute("msg", "用户角色添加成功");
		
		return "ok";
	}
	
	
	
	
	@GetMapping("/role.html")
	public String userRolePage(@RequestParam("id")Integer id,Model model) {
		
		//1、查出所有的
		List<TRole> roles = roleService.listAllRoles();
		
		//2、查询已有的角色
		List<TRole> hasRoles = roleService.listUserRoles(id);
		
		model.addAttribute("allRoles", roles);
		model.addAttribute("hasRoles", hasRoles);
		return "auth/user-role";
	}
	
	
	
	@PostMapping("/login")
	public String login(TUser user,HttpSession session,Model model) {
		//查出这个用户信息，并且放在session中，方便以后使用
		TUser loginUser = userService.login(user);
		
		if(loginUser!=null) {
			//成功去后台管理的主页
			//魔法值；
			session.setAttribute(AppConstant.LOGIN_USER, loginUser);
			
			//查出所有菜单，显示；菜单是后来每个页面都共用的放进session
			//List<TPermission> menus = permissionService.listMenus();
			List<TPermission> menus = permissionService.listUserMenus(loginUser.getId());
			session.setAttribute(AppConstant.USER_MENUS, menus);
			return "redirect:/main.html";
		}
		
		//失败返回登陆页面，提示用户名密码错误
		model.addAttribute("msg", "用户名或密码错误");
		return "login";
	}
	
	
	
	
	/**
	 * 注册请求处理
	 * @param user
	 * @param model
	 * @return
	 */
	//Rest格式的注解
	//@RequestMapping(value="/regist",method=RequestMethod.POST)
	@PostMapping("/regist")
	public String regist(TUser user,Model model,RedirectAttributes ra) {
		Map<String, String> exceptionTip = new HashMap<>();
		boolean regist  = false;
		try {
			regist = userService.regist(user);
		} catch (UserLoginAcctExistException e) {
			//用户名存在异常
			exceptionTip.put("loginAcct", e.getMessage());
		} catch (UserEmailExistException e) {
			//邮箱存在
			//${exception.email}
			exceptionTip.put("email", e.getMessage());
		}
		
		//判断注册成功还是失败
		if(!regist) {
			//保存异常信息
			model.addAttribute("excMsg", exceptionTip);
			//回显
			return "reg";
		}
		
		//只要参数是对象，这个对象会被自动放在model中；
		//1、自动放的key就是默认类名首字母小写，
		//2、或者key使用@ModelAttribute("user")注解的值
		//3、特殊情况；1是驼峰命名法才生效；
		//TUser（非驼峰） 如果首字母是大写并且第二个字母还是大写； TUser(TUser)  TableUser(tableUser)
		//结论；页面在特殊命名的情况下取值还是使用类名；
		//model.addAttribute("user", user);
		
		//注册失败返回注册页面回显数据，并提示
		//注册成功返回登陆页面提示可以登陆
		
		//以后所有表单提交去往的目的地都用重定向，防止重复提交；
		//model.addAttribute("msg", "注册成功，可以登陆了");
		ra.addFlashAttribute("msg", "注册成功，可以登陆了");
		return "redirect:/login.html";
	}

}
