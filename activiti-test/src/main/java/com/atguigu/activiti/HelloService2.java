package com.atguigu.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.mapper.AbcMapper;

//问题：
//	使用JavaDelegate的接口实现，不能自动注入spring容器中的其他组件
@Service("helloService02")
public class HelloService2 implements JavaDelegate {
	
	//
	
	AbcMapper abcMapper;
	
	
	
	public AbcMapper getAbcMapper() {
		return abcMapper;
	}
	
	@Autowired
	public void setAbcMapper(AbcMapper abcMapper) {
		System.out.println("注入的组件："+abcMapper);
		this.abcMapper = abcMapper;
	}

	public void haha(int i,int j) {
		System.out.println("他们的和为："+(i+j));
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("自动注入的对象："+getAbcMapper());
		Integer i = (Integer) execution.getVariable("i");
		Integer j = (Integer) execution.getVariable("j");
		
		//haha()
		haha(i,j);
	}

}
