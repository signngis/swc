package com.atguigu.restapi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.restapi.bean.AppResult;

/**
 * 1、统一的异常处理
 * 		1）、编写一个统一处理所有异常的类
 * 				@ControllerAdvice
 * 		2）、编写统一处理异常的方法
 * @author lfy
 *
 */
//Spring容器一定要扫描进来
@ControllerAdvice
public class AppExceptionHandler {
	
	//当前方法允许写一个Exception参数，代表捕获到的异常
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public AppResult<Exception> handleException(Exception exception) {
		return AppResult.fail(1,exception.getMessage(),exception);
	}
	
	
	

}
