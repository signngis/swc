package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.bean.TRole;
import com.atguigu.bean.TRoleExample;
import com.atguigu.bean.TRoleExample.Criteria;
import com.atguigu.bean.TUserRoleExample;
import com.atguigu.mapper.TRoleMapper;
import com.atguigu.mapper.TUserRoleMapper;
import com.atguigu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TUserRoleMapper userRoleMapper;
	

	@Override
	public List<TRole> listAllRoles() {
		// TODO Auto-generated method stub
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<TRole> listAllRoles(String condition) {
		// TODO Auto-generated method stub
		if (!StringUtils.isEmpty(condition)) {
			TRoleExample example = new TRoleExample();

			try {
				int id = Integer.parseInt(condition);
				example.createCriteria().andIdEqualTo(id);
			} catch (NumberFormatException e) {

			}

			Criteria nameLike = example.createCriteria().andNameLike("%" + condition + "%");
			example.or(nameLike);

			return roleMapper.selectByExample(example);

		}
		return roleMapper.selectByExample(null);
	}

	@Override
	public void saveRole(TRole role) {
		// TODO Auto-generated method stub
		roleMapper.insertSelective(role);
	}

	@Override
	public TRole getRoleById(Integer rid) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(rid);
	}

	@Override
	public void updateRole(TRole role) {
		// TODO Auto-generated method stub
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void batchDelete(List<Integer> idArray) {
		// TODO Auto-generated method stub
		TRoleExample example = new TRoleExample();
		if (idArray != null && idArray.size() >= 1) {
			
			example.createCriteria().andIdIn(idArray);
			roleMapper.deleteByExample(example);
		}

	}

	@Override
	public List<TRole> listUserRoles(Integer id) {
		// TODO Auto-generated method stub
		return roleMapper.selectUserRoles(id);
	}

	@Override
	public void insertUserRole(Integer uid, List<Integer> ridInts) {
		// TODO Auto-generated method stub
		roleMapper.insertUserRole(uid,ridInts);
	}

	@Override
	public void deleteUserRole(Integer uid, List<Integer> ridInts) {
		// TODO Auto-generated method stub
		//DELETE FROM `t_user_role` WHERE userid=3 AND roleid IN(5,1)
		TUserRoleExample example = new TUserRoleExample();
		example.createCriteria().andUseridEqualTo(uid).andRoleidIn(ridInts);
		userRoleMapper.deleteByExample(example);
	}

}
