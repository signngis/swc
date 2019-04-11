package com.atguigu.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.bean.TProject;
import com.atguigu.bean.TUser;
import com.atguigu.bean.TUserExample;
import com.atguigu.bean.TUserExample.Criteria;
import com.atguigu.enume.AuthStatusEnum;
import com.atguigu.enume.ProjectStatusEnum;
import com.atguigu.mapper.TProjectMapper;
import com.atguigu.mapper.TUserMapper;
import com.atguigu.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 使用Spring单元测试
 * 
 * 1、导入spring-test和junit的依赖
 * 2、不直接使用原生的junit，使用Spring的单元测试
 * 		@RunWith(value=SpringJUnit4ClassRunner.class):使用Spring驱动单元测试
 * 		@ContextConfiguration(locations= {"classpath:spring-beans.xml",
		"classpath:spring-mybatis.xml",
		"classpath:spring-tx.xml"})
		
 * @author lfy
 *
 */
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-beans.xml",
		"classpath:spring-mybatis.xml",
		"classpath:spring-tx.xml",
		"classpath:spring-activiti.xml"})
public class MapperTest {
	
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Test
	public void complete02() {
		List<Task> list = taskService.createTaskQuery().processInstanceId("15001").list();
		for (Task task : list) {
			System.out.println(task);
			taskService.complete(task.getId());
			System.out.println(task+"=完成");
		}
	}
	
	
	//ProcessInstance[15001]
	@Test
	public void complete() {
		//${servAuth==true}
		//${reAuth==true}
		//id,authStatusEnum
		//${userEmail}
		// ${username} 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("servAuth", true);
		map.put("reAuth", true);
		map.put("id", 1);
		map.put("authStatusEnum", AuthStatusEnum.AUTHED);
		map.put("userEmail", "leifengyang@atguigu.com");
		map.put("username", "leifengyang");
		ProcessInstance processInstance = runtimeService.startProcessInstanceById("realauth:2:12504",map);
		System.out.println(processInstance);
	}
	
	//DeploymentEntity[id=12501, name=null]
	//DeploymentEntity[id=10001, name=null]
	@Test
	public void deployTest() {
		Deployment deploy = repositoryService.createDeployment()
			.addClasspathResource("实名审核流程-v1.0.bpmn")
			.deploy();
		System.out.println(deploy);
	}
	
	
	
	
	@Autowired
	TUserMapper userMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TProjectMapper projectMapper;
	
	@Test
	public void test333() {
		
		PageHelper.startPage(2, 5);
		//ProjectStatusEnum valueOf = ProjectStatusEnum.valueOf("1");
		List<TProject> list = projectMapper.selectProjects(null,null,null);
		
		PageInfo info = new PageInfo<>(list);
		
		for (TProject tProject : list) {
			System.out.println(tProject.getName()+"==>"+tProject.getId());
		}
		
		System.out.println("总记录数："+info.getTotal());
		System.out.println("总页码："+info.getPages());
	}
	
	
	
	/**
	 * 1、引入mybatis PageHelper分页插件
	 * <dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper</artifactId>
		</dependency>
	 *
	 *2、在mybatis全局配置文件中配置使用分页插件
	 *https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
	 *
	 *
	 *
	 */
	@Test
	public void testPage() {
		
		//只需要在原有方法之前调用PageHelper.startPage(哪一页, 每页的数据大小);
		//只会让他的下一个查询方法变为分页查询 ThreadLocal
		//select * from xxx where xxx limit index,size
		PageHelper.startPage(16, 5);
		//查询结果
		List<TUser> listAll = userService.listAll();
		//使用PageInfo包装
		PageInfo<TUser> info = new PageInfo<>(listAll,5);
		
		//List<TUser> listAll2 = userService.listAll();
		
		
		System.out.println(listAll.size());
		//System.out.println(listAll2.size());
		System.out.println("当前第几页："+info.getPageNum());
		System.out.println("总页码："+info.getPages());
		System.out.println("总记录数："+info.getTotal());
		System.out.println("是否有上一页："+info.isHasPreviousPage());
		System.out.println("是否有下一页："+info.isHasNextPage());
		System.out.println("上一页："+info.getPrePage());
		System.out.println("下一页："+info.getNextPage());
		System.out.println("末页："+info.getLastPage());
		System.out.println("连续分页：");
		//获取导航页
		int[] nums = info.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(i+"\t");
		}
		
		//分页的数据
		List<TUser> list = info.getList();
	}
	
	
	
	//测试带条件，有选择的复杂更新
	@Test
	public void testUpdate() {
		
		//update t_user SET username = ? WHERE ( loginacct = ? and userpswd = ? )
		//需要更新的字段设置上值即可，其他不要更新的必须为null
		TUser user = new TUser();
		user.setUsername("哈哈哈");
		
		TUserExample example = new TUserExample();
		example.createCriteria().andLoginacctEqualTo("leifengyang").andUserpswdEqualTo("123456");
		userMapper.updateByExampleSelective(user, example);
		
	}
	
	
	// 本表内的所有查询都可以用
	// 如果需要连表查询，自己额外定义方法和SQL；
	@Test
	public void testQuery() {
		//1、简单查询
		//select id, loginacct, userpswd, username, email, createtime from t_user where id = ? 
//		TUser user = userMapper.selectByPrimaryKey(2);
//		System.out.println(user);
		
		//2、复杂条件（只有and）   xxxExample用来封装xxx的查询条件
		//select id, loginacct, userpswd, username, email, createtime from t_user WHERE ( loginacct = ? and userpswd = ? )
		TUserExample example = new TUserExample();
		//2.1）、创建一组条件；只要是and连接我们就可以无限往下链式调用即可，对应条件找对应方法
//		Criteria criteria = example.createCriteria();
//		criteria.andLoginacctEqualTo("aaaaa").andUserpswdEqualTo("bbbbb");
		
		
		//3、复杂条件（and or混合）
		//select id, loginacct, userpswd, username, email, createtime from t_user WHERE ( loginacct = ? and userpswd = ? ) or( id = ? and email = ? ) 
		// where (loginacct=xx and userpswd=xxx) or (id=1 and email=xxx)
//		Criteria criteria1 = example.createCriteria();
//		criteria1.andLoginacctEqualTo("aaaaa").andUserpswdEqualTo("xxx");
//		
//		Criteria criteria2 = example.createCriteria();
//		criteria2.andIdEqualTo(1).andEmailEqualTo("xxxx");
		//将其他条件和第一组默认的连接起来
//		example.or(criteria2);
		
		
		//4、example没有内容；就是查询所有[null和example没内容都是查询所有]
		//select id, loginacct, userpswd, username, email, createtime from t_user
		userMapper.selectByExample(null);
	}
	
	
	@Test
	public void test02() {
		TUser user = new TUser();
		user.setUsername("xxxx");
		user.setEmail("xxxx");
		user.setLoginacct("aaaaa");
		user.setUserpswd("bbbbb");
		//user.setCreatetime("2017-12-12");
		//insert into t_user ( loginacct, userpswd, username, email ) values ( ?, ?, ?, ? ) 
		userMapper.insertSelective(user);
	}

}
