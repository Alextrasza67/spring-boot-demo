package com.github.spring.scheduled;

import com.github.QuartzApplicationTests;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/7/13.
 */
public class ScheduledDemo3Test extends QuartzApplicationTests implements ApplicationContextAware {

    @Test
    public void test(){

        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ScheduledDemo3.class);
        defaultListableBeanFactory.registerBeanDefinition("scheduledDemo3",beanDefinitionBuilder.getBeanDefinition());

        ScheduledDemo3 bean = (ScheduledDemo3) applicationContext.getBean("scheduledDemo3");
        System.out.println("scheduledDemo3 in applicationContext is "+bean);
        //上面两行 注释掉 任务不会执行

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}