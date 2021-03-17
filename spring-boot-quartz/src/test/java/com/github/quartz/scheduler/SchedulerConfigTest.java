package com.github.quartz.scheduler;

import com.github.QuartzApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/7/13.
 */
public class SchedulerConfigTest extends QuartzApplicationTests{

    @Autowired
    private SchedulerConfig schedulerConfig;

    @Test
    public void executeScheduler() throws Exception {
        schedulerConfig.executeScheduler();
    }

}