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
 * 测试包含网关
 * @author lfy
 *
 */
public class ActivitiInclusiveGatewayTest {
	
	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	
	//DeploymentEntity[id=30001, name=null]
	//DeploymentEntity[id=72501, name=null]
	//DeploymentEntity[id=77501, name=null]
	//DeploymentEntity[id=87501, name=null]

	@Test
	public void deploy() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		Deployment deploy = repositoryService.createDeployment()
			.addClasspathResource("开发商审核-包含.bpmn")
			.deploy();
		
		System.out.println(deploy);
	}
	
	//processInstance[id=32501]
	//ProcessInstance[75001]
	//ProcessInstance[80001]
	//ProcessInstance[90001]
	//ProcessInstance[97501]
	@Test
	public void startProcess() {
		RepositoryService repositoryService = engine.getRepositoryService();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.deploymentId("87501").singleResult();
		System.out.println(processDefinition.getId());
		
		//启动这个流程
		RuntimeService runtimeService = engine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
		System.out.println(processInstance);
		
	}
	
	@Test
	public void completeTask() {
		TaskService taskService = engine.getTaskService();
		List<Task> list = taskService.createTaskQuery()
				.processInstanceId("97501").list();
		int i = 0;
		for (Task task : list) {
			System.out.println(task);
			
			//xxxxxxx
			if(i%2 == 0) {
				taskService.complete(task.getId());
				System.out.println(task+"==》完成");
				//i++;
			}
//			Map<String, Object> variables = new HashMap<>();
//			variables.put("money", 90);
//			variables.put("area", 400);
//			
////			//传入流程变量完成任务
//			taskService.complete(task.getId(),variables);
//			System.out.println(task+"==》完成");
		}
	}
	
	//查询完成的流程实例
	@Test
	public void finishedProcessInstance() {
		HistoryService historyService = engine.getHistoryService();
		List<HistoricProcessInstance> list = historyService
			.createHistoricProcessInstanceQuery()
			.processDefinitionKey("myProcess")
			.finished().list();
		for (HistoricProcessInstance historicProcessInstance : list) {
			System.out.println(historicProcessInstance.getId());
		}
	}

}
