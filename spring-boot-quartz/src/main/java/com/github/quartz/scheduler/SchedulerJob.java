package com.github.quartz.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by alex on 2018/7/13.
 */
@Slf4j
public class SchedulerJob implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("This is a msg from SchedulerJob. testKey's value is {}",jobExecutionContext.getMergedJobDataMap().get("testKey"));
    }
}
