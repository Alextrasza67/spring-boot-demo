package com.github.quartz.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 2018/7/13.
 */
@Component
public class SchedulerConfig {

    public void executeScheduler() throws SchedulerException, InterruptedException {
        // 获取Scheduler实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        System.out.println("scheduler.start");


        //具体任务.
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("testKey","testValue");
        JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class).withIdentity("job1", "group1").setJobData(jobDataMap).build();


        //触发时间点. (每5秒执行1次.)
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                            .withSchedule(simpleScheduleBuilder).build();

        // 交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);
        //睡眠10秒.
        TimeUnit.SECONDS.sleep(10);

        scheduler.deleteJob(jobDetail.getKey());
        System.out.println("remove job");

        //睡眠20秒.
        TimeUnit.SECONDS.sleep(5);

        scheduler.shutdown();//关闭定时任务调度器.
        System.out.println("scheduler.shutdown");
    }

}
