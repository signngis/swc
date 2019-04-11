package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.TUser;
import com.atguigu.exception.UserEmailExistException;
import com.atguigu.exception.UserLoginAcctExistException;

public interface UserService {

	boolean regist(TUser user) throws UserLoginAcctExistException,UserEmailExistException;
	
	void checkLoginAcct(TUser user);
	void checkEmail(TUser user);

	//登陆返回登陆后的用户
	TUser login(TUser user);

	List<TUser> listAll();

	//查询符合条件的所有用户
	List<TUser> listAll(String condition);

	boolean saveUser(TUser user) throws UserLoginAcctExistException,UserEmailExistException;

	TUser getUser(Integer id);

	void updateUser(TUser user);

	void batchDelete(List<Integer> idArray);

}
