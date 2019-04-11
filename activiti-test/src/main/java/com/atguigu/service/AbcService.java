package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.mapper.AbcMapper;


public class AbcService {
	
	//
	AbcMapper abcMapper;
	
	
	
	public AbcMapper getAbcMapper() {
		return abcMapper;
	}



	public void setAbcMapper(AbcMapper abcMapper) {
		this.abcMapper = abcMapper;
	}



	public void helloService(int i) {
		System.out.println("service 获取到 mapper执行操作"+abcMapper+"==>"+i);
		abcMapper.hello(i);
		System.out.println(abcMapper);
	}
	

}
