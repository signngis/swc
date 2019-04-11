package com.atguigu.test;

import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ActivitiEmailTest {
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void print() {
		System.out.println(engine);
	}
	
	@Test
	public void completeTask() {
		
		TaskService taskService = engine.getTaskService();
		
		//1、查询当前流程还有那些任务
		List<Task> list = taskService.createTaskQuery().processInstanceId("15001").list();
		for (Task task : list) {
			System.out.println(task.getId()+"==>"+task.getName());
			//2、签收任务【可选】
			//taskService.claim(task.getId(), "lisi");
			//3、完成
			taskService.complete(task.getId());
			//System.out.println("任务完成了"+task.getName());
			//已经运行完成的任务会从运行时表中删除；
		}
	}
	
	/**
	 * 启动流程
	 * 	ProcessInstance[15001]
	 */
	//ProcessDefinitionEntity[emailtest:1:107504]
	//ProcessInstance[110001]
	@Test
	public void startProcess() {
		RuntimeService runtimeService = engine.getRuntimeService();
		
		//runtimeService.startprocessInstanceBy
		//查出某个流程定义的id；查询最新版的请假流程
		RepositoryService repositoryService = engine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("emailtest")
			.latestVersion()
			.singleResult();
		System.out.println(processDefinition);
		
		//其他Service 的查询模式；
		//  xxxxService.createXxxQuery().xxx().xxx().xxx().list()/.singleResult()./listPage()
		
		
		//按照流程定义的id精确启动某个流程定义；-----流程实例；
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println(processInstance);
		
		
		
	}
	
	/**
	 * 1、绘制流程图；
	 * 2、部署流程；（流程图信息存储到数据库）
	 */
	
	//部署成功DeploymentEntity[id=107501, name=null]
	@Test
	public void deployTest() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		//创建一次部署
		Deployment deployment = repositoryService.createDeployment()
			.addClasspathResource("邮件任务测试.bpmn")
			.deploy();
		
		System.out.println("部署成功"+deployment);
	}
	
	
	
	
	@Test
	public void getProcessEngine() {
		
		
		System.out.println(engine);
		
	}

}
