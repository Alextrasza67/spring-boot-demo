package com.github.quartz.scheduler;

import com.github.QuartzDbApplicationTests;
import com.github.quartz.entity.QuartzEntity;
import org.junit.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/7/31.
 */
public class SchedulerOperatorTest extends QuartzDbApplicationTests{

    @Autowired
    private Scheduler scheduler;

    @Test
    public void test() throws Exception{
        if(scheduler == null){
            throw new NullPointerException();
        }

        SchedulerOperator op = new SchedulerOperator(scheduler);

        QuartzEntity quartz = new QuartzEntity();
        quartz.setJobName("custom_test_1");
        quartz.setJobGroup("quartz-db");
        quartz.setDescription("测试任务");
        quartz.setJobClassName("com.github.quartz.job.CustomJob");
        quartz.setCronExpression("0/1 * * * * ?");

        op.save(quartz);

        Thread.sleep(5000);

        op.pause(quartz);

        Thread.sleep(3000);

        op.trigger(quartz);

        Thread.sleep(3000);

        op.resume(quartz);

        Thread.sleep(3000);

        op.remove(quartz);

        Thread.sleep(3000);

    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void trigger() throws Exception {
    }

    @Test
    public void pause() throws Exception {
    }

    @Test
    public void resume() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}