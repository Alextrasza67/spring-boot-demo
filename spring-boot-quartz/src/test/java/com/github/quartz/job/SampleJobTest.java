package com.github.quartz.job;

import com.github.QuartzApplicationTests;
import org.junit.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 2018/7/13.
 */
public class SampleJobTest extends QuartzApplicationTests{

    @Autowired
    private Scheduler scheduler;

    @Test
    public void testJob() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class).withIdentity("job1", "group1").build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().build();

        // 交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);
        TimeUnit.SECONDS.sleep(10);
    }


}