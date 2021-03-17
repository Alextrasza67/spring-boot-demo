package com.github.activiti.service;

import com.github.activiti.ActivitiApplicationTests;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 2019/4/17.
 */
public class ActivitiServiceForYinbaoTest extends ActivitiApplicationTests {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;


    private String instanceId;

    @Test
    public void startWorkFlow() {
        //启动流程实例，字符串"vacation"是BPMN模型文件里process元素的id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("YinbaoPuhuiModel");
        instanceId = processInstance.getId();
        System.out.println(processInstance.getId());
    }


    @Test
    public void doLoanApply(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("40008").singleResult();
        System.out.println(vacationApply.getId());

        taskService.complete(vacationApply.getId());
    }

    @Test
    public void doLoanResult(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("40008").singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("status", "S");

        taskService.complete(vacationApply.getId(), args);
    }


    @Test
    public void doLoanNotice(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("40008").singleResult();
        System.out.println(vacationApply.getId());

        taskService.complete(vacationApply.getId());
    }

    @Test
    public void doLoanDetail(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("40008").singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("status", "E");

        taskService.complete(vacationApply.getId(), args);
    }

    @Test
    public void doToStep3(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("success", false);
        args.put("orderId", System.currentTimeMillis());

        taskService.complete(vacationApply.getId(), args);
    }


    @Test
    public void doToStep4(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("success", false);
        //值会覆盖

        taskService.complete(vacationApply.getId(), args);
    }


    @Test
    public void complete(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("27501").singleResult();
        System.out.println(vacationApply.getId());

        taskService.complete(vacationApply.getId());
    }

    @Test
    public void delete(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("7501").singleResult();
        System.out.println(vacationApply.getId());

        runtimeService.deleteProcessInstance(vacationApply.getProcessInstanceId(),"test delete");
    }



}