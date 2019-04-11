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

public class ActivitiTest {
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	
	
	/**
	 * 测试排他网关
	 */
	@Test
	public void testExcluded() {
		
	}
	
	
	@Test
	public void queryHistory() {
		HistoryService service = engine.getHistoryService();
		//1、哪些流程实例都结束
		List<HistoricProcessInstance> list = service.createHistoricProcessInstanceQuery()
			.processDefinitionId("vocation-request-plus:2:12504").list();
		for (HistoricProcessInstance hpi : list) {
			System.out.println(hpi.getId()+"==>"+hpi.getName());
			System.out.println(hpi.getStartTime()+"==>"+hpi.getEndTime());
		}
		
		
		//2、获取所有 15001 号流程实例的所有任务信息
		List<HistoricTaskInstance> list2 = service.createHistoricTaskInstanceQuery().processInstanceId("15001").list();
		for (HistoricTaskInstance hti : list2) {
			System.out.println(hti.getId()+"==>"+hti.getName()+"==>"+hti.getAssignee());
		}
	}
	
	
	@Test
	public void querySomeoneTask() {
		TaskService taskService = engine.getTaskService();
		List<Task> list = taskService.createTaskQuery().taskAssignee("lisi").list();
		for (Task task : list) {
			System.out.println(task.getId()+"==>"+task.getName());
			taskService.complete(task.getId());
			System.out.println("任务完成...");
		}
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
	@Test
	public void startProcess() {
		RuntimeService runtimeService = engine.getRuntimeService();
		
		//runtimeService.startprocessInstanceBy
		//查出某个流程定义的id；查询最新版的请假流程
		RepositoryService repositoryService = engine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("vocation-request-plus")
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
	
	@Test
	public void deployTest() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		//创建一次部署
		Deployment deployment = repositoryService.createDeployment()
			.addClasspathResource("请假流程-plus.bpmn")
			.name("这是一个测试流程")
			.category("测试分类")
			.deploy();
		
		System.out.println("部署成功"+deployment);
	}
	
	
	
	
	@Test
	public void getProcessEngine() {
		
		
		System.out.println(engine);
		
	}

}
