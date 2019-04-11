package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.TPermission;
import com.atguigu.service.PermissionService;

@RequestMapping("/per")
@RestController
public class PermissionController {
	
	@Autowired
	PermissionService permissionService;
	
	//查询当前角色的所有菜单
	@GetMapping("/roleper.json")
	public List<TPermission> rolePermissions(@RequestParam("rid")Integer rid){
		
		return permissionService.listRolePermissions(rid);
	}
	
	//角色id，以及这个角色勾选的所有权限id；
	@PostMapping("/assign")
	public String assignPermission(@RequestParam("rid")Integer rid,
			@RequestParam("pids")String pids) {
		List<Integer> pidInt = new ArrayList<>();
		String[] split = pids.split(",");
		for (String str : split) {
			try {
				pidInt.add(Integer.parseInt(str));
			}catch (Exception e) {
			}
		}
		
		permissionService.assignRolePermission(rid,pidInt);
		
		return "ok";
	}
	
	
	@GetMapping("/parents.json")
	public List<TPermission> getParentPermission(){
		
		return permissionService.listParents();
	}
 	
	//删除
	@GetMapping("/delete")
	public String deletePermission(@RequestParam("ids") String ids) {
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
			permissionService.batchDelete(idArray);
		}

		return "ok";
	}
	
	
	//更新
	@PostMapping("/update")
	public String updatePermisson(TPermission permission) {
		permissionService.updatePermission(permission);
		return "ok";
	}
	
	//保存
	@PostMapping("/save")
	public String addPermisson(TPermission permission) {
		permissionService.addPermission(permission);
		return "ok";
	}
	
	//查询出所有菜单
	@GetMapping("/list.json")
	public List<TPermission> getAll(){
		return permissionService.listMenus();
	}
	


}
