package com.atguigu.mapper;

import org.springframework.stereotype.Repository;

@Repository
public class AbcMapper {
	
	public void hello(int i) {
		System.out.println("数据库 第 "+i+" 条记录被修改");
	}

}
