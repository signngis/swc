package com.atguigu.exception;

//用户邮箱已存在异常
public class UserEmailExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserEmailExistException() {
		super("用户邮箱已存在");
	}

}
