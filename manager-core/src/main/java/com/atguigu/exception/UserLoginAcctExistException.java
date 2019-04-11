package com.atguigu.exception;

//用户账号已存在异常
public class UserLoginAcctExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserLoginAcctExistException() {
		super("用户账户已存在");
	}

}
