package com.github.activiti.service;

import com.alibaba.fastjson.JSON;
import com.github.activiti.ActivitiApplicationTests;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by alex on 2019/4/17.
 */
public class ActivitiServiceTest extends ActivitiApplicationTests {

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



    @Test
    public void startWorkFlow() {
        //启动流程实例，字符串"vacation"是BPMN模型文件里process元素的id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testKey");
        System.out.println(processInstance.getId());
    }


    @Test
    public void doToStep2(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("5").singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("success", true);
        args.put("orderId", System.currentTimeMillis());

        taskService.complete(vacationApply.getId(), args);
    }

    @Test
    public void doToStep3(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("5001").singleResult();
        System.out.println(vacationApply.getId());

        Map<String, Object> args = new HashMap<>();
        args.put("success", false);
        args.put("orderId", System.currentTimeMillis());

        taskService.complete(vacationApply.getId(), args);
    }


    @Test
    public void doToStep4(){

        Task vacationApply = taskService.createTaskQuery().processInstanceId("27501").singleResult();
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