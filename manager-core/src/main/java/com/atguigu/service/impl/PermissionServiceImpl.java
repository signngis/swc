package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.bean.TPermission;
import com.atguigu.bean.TPermissionExample;
import com.atguigu.bean.TRoleExample;
import com.atguigu.bean.TRolePermissionExample;
import com.atguigu.mapper.TPermissionMapper;
import com.atguigu.mapper.TRolePermissionMapper;
import com.atguigu.service.PermissionService;


@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	TPermissionMapper permissionMapper;
	
	@Autowired
	TRolePermissionMapper rolePermissionMapper;
	
	@Override
	public List<TPermission> listMenus() {
		
		return permissionMapper.selectByExample(null);
	}

	@Override
	public List<TPermission> listUserMenus(Integer id) {
		// TODO Auto-generated method stub
		
		return permissionMapper.listUserMenus(id);
	}

	@Override
	public void addPermission(TPermission permission) {
		// TODO Auto-generated method stub
		permissionMapper.insertSelective(permission);
	}

	@Override
	public void updatePermission(TPermission permission) {
		// TODO Auto-generated method stub
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public void batchDelete(List<Integer> idArray) {
		// TODO Auto-generated method stub
		TPermissionExample example = new TPermissionExample();
		if (idArray != null && idArray.size() >= 1) {
			
			example.createCriteria().andIdIn(idArray);
			permissionMapper.deleteByExample(example);
		}
	}

	@Override
	public List<TPermission> listParents() {
		// TODO Auto-generated method stub
		TPermissionExample example = new TPermissionExample();
		example.createCriteria().andPidEqualTo(0);
		
		return permissionMapper.selectByExample(example);
	}

	@Transactional
	@Override
	public void assignRolePermission(Integer rid, List<Integer> pidInt) {
		//1、删除角色之前拥有的权限
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andRoleidEqualTo(rid);
		rolePermissionMapper.deleteByExample(example);
		
		//2、添加新的权限
		rolePermissionMapper.insertRolePermission(rid,pidInt);
	}

	@Override
	public List<TPermission> listRolePermissions(Integer rid) {
		// TODO Auto-generated method stub
		return permissionMapper.listRolePermissions(rid);
	}

}
