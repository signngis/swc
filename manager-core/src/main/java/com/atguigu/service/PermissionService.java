package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.TPermission;

public interface PermissionService {
	
	//查出所有菜单
	public List<TPermission> listMenus();

	public List<TPermission> listUserMenus(Integer id);

	public void addPermission(TPermission permission);

	public void updatePermission(TPermission permission);

	public void batchDelete(List<Integer> idArray);

	//查询所有的父菜单
	public List<TPermission> listParents();

	/**
	 * 分配角色对应的权限
	 * @param rid     角色id
	 * @param pidInt  权限id集合
	 */
	public void assignRolePermission(Integer rid, List<Integer> pidInt);

	//按照角色id查询所有的权限
	public List<TPermission> listRolePermissions(Integer rid);

}
