package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.TRole;

public interface RoleService {

	//获取所有角色
	List<TRole> listAllRoles();

	//带模糊条件的查询
	List<TRole> listAllRoles(String condition);

	void saveRole(TRole role);

	TRole getRoleById(Integer rid);

	void updateRole(TRole role);

	void batchDelete(List<Integer> idArray);

	//查出用户已有的角色
	List<TRole> listUserRoles(Integer id);

	//插入用户对应的角色id
	void insertUserRole(Integer uid, List<Integer> ridInts);

	//删除用户对应的角色
	void deleteUserRole(Integer uid, List<Integer> ridInts);

}
