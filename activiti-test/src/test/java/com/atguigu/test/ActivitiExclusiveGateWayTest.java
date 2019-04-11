package com.atguigu.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 测试排他网关
 * @author lfy
 *
 */
public class ActivitiExclusiveGateWayTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	
	//DeploymentEntity[id=30001, name=null]
	@Test
	public void deploy() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		Deployment deploy = repositoryService.createDeployment()
			.addClasspathResource("请假流程-排他网关.bpmn")
			.deploy();
		
		System.out.println(deploy);
	}
	
	//processInstance[id=32501]
	@Test
	public void startProcess() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.deploymentId("30001").singleResult();
		System.out.println(processDefinition.getId());
		
		//启动这个流程
		RuntimeService runtimeService = engine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println(processInstance.getId());
		
	}
	
	@Test
	public void completeTask() {
		TaskService taskService = engine.getTaskService();
		List<Task> list = taskService.createTaskQuery().processInstanceId("32501").list();
		for (Task task : list) {
			System.out.println(task);
			//xxxxxxx
//			Map<String, Object> variables = new HashMap<>();
//			variables.put("glAuth", true);
//			variables.put("ceoAuth", true);
//			//传入流程变量完成任务
			taskService.complete(task.getId());
			System.out.println(task+"==》完成");
		}
	}
	
	//查询完成的流程实例
	@Test
	public void finishedProcessInstance() {
		HistoryService historyService = engine.getHistoryService();
		List<HistoricProcessInstance> list = historyService
			.createHistoricProcessInstanceQuery()
			.processDefinitionKey("vocation-request-new")
			.finished().list();
		for (HistoricProcessInstance historicProcessInstance : list) {
			System.out.println(historicProcessInstance.getId());
		}
	}

}
