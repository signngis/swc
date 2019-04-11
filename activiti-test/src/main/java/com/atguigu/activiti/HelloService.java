package com.atguigu.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.mapper.AbcMapper;

public class HelloService implements JavaDelegate {
	//@Autowired
	AbcMapper abcMapper;
	
	public void haha(int i,int j) {
		System.out.println("他们的和为："+(i+j));
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Integer i = (Integer) execution.getVariable("i");
		Integer j = (Integer) execution.getVariable("j");
		
		//haha()
		haha(i,j);
	}

}
