package com.github.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 2018/7/13.
 */
@Component
@Slf4j
public class ScheduledDemo {


    @Scheduled(fixedDelay = 1000)
    public void fixedDelay(){
        // delay 是时间间隔 上次执行完毕了 才会开始计时
        log.info("This is a msg from fixedDelay.");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 1000)
    public void fixedRate(){
        // rate 是时间间隔 不考虑运行时间
        Double d = Math.random()*10;
        log.info("This is a msg from fixedRate "+d+" . ");
        try {
            Thread.sleep(200);
            //2000 会影响后续任务 包括delay的 以及 ScheduledDemo2里的
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("fixedRate "+d+" end.");
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void cron(){
        // 根据表达式运行
        log.info("This is a msg from cron.");
    }
}
