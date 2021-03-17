package com.github.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 2018/7/13.
 */
@Slf4j
public class ScheduledDemo3 {


    @Scheduled(fixedDelay = 1000)
    public void fixedDelay(){
        // delay 是时间间隔 上次执行完毕了 才会开始计时
        log.info("This is a msg from ScheduledDemo3 fixedDelay.");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
