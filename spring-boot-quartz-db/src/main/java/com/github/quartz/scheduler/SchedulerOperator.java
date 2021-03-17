package com.github.quartz.scheduler;

import com.github.quartz.entity.QuartzEntity;
import com.github.quartz.exception.CustomSchedulerException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * Created by alex on 2018/7/31.
 */
@Slf4j
public class SchedulerOperator {

    private Scheduler scheduler;

    public SchedulerOperator(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    public void save(QuartzEntity quartz) throws CustomSchedulerException {
        log.info("新增任务");
        try {
            //如果是修改  展示旧的 任务
            if (quartz.getOldJobGroup() != null) {
                JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
                scheduler.deleteJob(key);
            }
            Class cls = Class.forName(quartz.getJobClassName());
            cls.newInstance();
            //构建job信息
            JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                    quartz.getJobGroup())
                    .withDescription(quartz.getDescription()).build();
            // 触发时间点
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                    .startNow().withSchedule(cronScheduleBuilder).build();
            //交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomSchedulerException("新增任务失败", e);
        }
    }

    public void trigger(QuartzEntity quartz) throws CustomSchedulerException {
        log.info("触发任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new CustomSchedulerException("触发任务失败", e);
        }
    }

    public void pause(QuartzEntity quartz) throws CustomSchedulerException {
        log.info("停止任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new CustomSchedulerException("停止任务失败", e);
        }
        return;
    }

    public void resume(QuartzEntity quartz) throws CustomSchedulerException {
        log.info("恢复任务");
        try {
            JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new CustomSchedulerException("恢复任务失败", e);
        }
    }

    public void remove(QuartzEntity quartz) throws CustomSchedulerException {
        log.info("移除任务");
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
            System.out.println("removeJob:" + JobKey.jobKey(quartz.getJobName()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomSchedulerException("移除任务失败", e);
        }
    }
}
