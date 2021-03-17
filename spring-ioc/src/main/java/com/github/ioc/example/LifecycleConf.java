package com.github.ioc.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifecycleConf {

    @Bean(initMethod = "initMethod", destroyMethod = "destoryMethod")
    public LifecycleExample lifecycleExample(){
        System.out.println("LifecycleExampleTest.lifecycleExample");
        return new LifecycleExample();
    }
}
