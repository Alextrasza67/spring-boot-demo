package com.github.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * Created by alex on 2018/7/31.
 */
@Slf4j
public class CustomJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        // 取得job名称
        String jobName = jobDetail.getClass().getName();
        log.info(jobName + "开始运行！");
        log.info("Job Classname: " + jobDetail.getClass().getSimpleName());
        //取得job的类
        log.info("Job Class: " + jobDetail.getJobClass());
        //取得job开始时间
        log.info("fired at " + context.getFireTime());
        //取得job下次触发时间
        log.info("Next fire time " + context.getNextFireTime());

        JobDataMap dataMap = jobDetail.getJobDataMap();
        if (dataMap.getKeys() != null) {
            for (String key : dataMap.getKeys()) {
                log.info("key : " + key + " ; value : " + dataMap.getString(key));
            }
        }
        log.info(jobName + "运行结束！");
    }
}
