package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.bean.TUser;
import com.atguigu.bean.TUserExample;
import com.atguigu.bean.TUserExample.Criteria;
import com.atguigu.exception.UserEmailExistException;
import com.atguigu.exception.UserLoginAcctExistException;
import com.atguigu.mapper.TUserMapper;
import com.atguigu.service.UserService;
import com.atguigu.util.MD5Utils;
import com.atguigu.util.MyDateUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	TUserMapper userMapper;

	@Override
	public boolean regist(TUser user) {
		// 注册信息初始化
		user.setUsername(user.getLoginacct());
		user.setCreatetime(MyDateUtils.getDateString());
		// 密码加密存储
		user.setUserpswd(MD5Utils.digestPassword(user.getUserpswd()));

		// 检查用户名
		checkLoginAcct(user);

		// 检查邮箱
		checkEmail(user);

		userMapper.insertSelective(user);
		return true;
	}

	@Override
	public void checkLoginAcct(TUser user) {
		// 1、用户名必须唯一：
		TUserExample loginAcctExample = new TUserExample();
		loginAcctExample.createCriteria().andLoginacctEqualTo(user.getLoginacct());
		long l = userMapper.countByExample(loginAcctExample);
		if (l > 0) {
			throw new UserLoginAcctExistException();
		}

	}

	@Override
	public void checkEmail(TUser user) {
		// TODO Auto-generated method stub
		// 2、邮箱必须唯一；
		TUserExample emailExample = new TUserExample();
		emailExample.createCriteria().andEmailEqualTo(user.getEmail());
		long m = userMapper.countByExample(emailExample);
		if (m > 0) {
			throw new UserEmailExistException();
		}
	}

	@Override
	public TUser login(TUser user) {
		TUserExample example = new TUserExample();
		example.createCriteria().andLoginacctEqualTo(user.getLoginacct())
				.andUserpswdEqualTo(MD5Utils.digestPassword(user.getUserpswd()));
		List<TUser> list = userMapper.selectByExample(example);
		return list.size() == 1 ? list.get(0) : null;
	}

	@Override
	public List<TUser> listAll() {

		return userMapper.selectByExample(null);
	}

	@Override
	public List<TUser> listAll(String condition) {
		if (StringUtils.isEmpty(condition)) {
			return userMapper.selectByExample(null);
		}

		TUserExample example = new TUserExample();
		try {
			// 如果能有id，按照id检索
			int id = Integer.parseInt(condition);
			example.createCriteria().andIdEqualTo(id);
		} catch (NumberFormatException e) {
		}

		Criteria andLoginacctLike = example.createCriteria().andLoginacctLike("%" + condition + "%");

		Criteria andUsernameLike = example.createCriteria().andUsernameLike("%" + condition + "%");

		Criteria andCreatetimeLike = example.createCriteria().andCreatetimeLike("%" + condition + "%");

		example.or(andLoginacctLike);
		example.or(andUsernameLike);
		example.or(andCreatetimeLike);

		return userMapper.selectByExample(example);
	}

	@Override
	public boolean saveUser(TUser user) throws UserLoginAcctExistException,UserEmailExistException{
		// TODO Auto-generated method stub
		// 注册信息初始化
		user.setCreatetime(MyDateUtils.getDateString());
		// 密码加密存储
		user.setUserpswd(MD5Utils.digestPassword("123456"));

		// 检查用户名
		checkLoginAcct(user);

		// 检查邮箱
		checkEmail(user);

		userMapper.insertSelective(user);
		return false;
	}

	//获取单个用户
	@Override
	public TUser getUser(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	//修改用户
	@Override
	public void updateUser(TUser user) {
		// TODO Auto-generated method stub
		//检查邮箱是否重复
		checkEmail(user);
		
		user.setLoginacct(null);
		
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void batchDelete(List<Integer> idArray) {
		// TODO Auto-generated method stub
		// delete from xxx where id IN(1,2,3,4)
		/*for (Integer integer : idArray) {
			userMapper.deleteByPrimaryKey(integer);
		}*/
		TUserExample example = new TUserExample();
		example.createCriteria().andIdIn(idArray);
		userMapper.deleteByExample(example);
		
	}

}
