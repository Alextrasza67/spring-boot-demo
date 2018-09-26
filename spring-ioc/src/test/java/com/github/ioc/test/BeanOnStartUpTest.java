package com.github.ioc.test;

import com.github.IocApplicationTests;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/6/12.
 */
public class BeanOnStartUpTest extends IocApplicationTests implements ApplicationContextAware{

    @Test
    public void test1(){
        BeanOnStartUp bean = (BeanOnStartUp) applicationContext.getBean("beanOnStartUp");

        if(bean!=null){
            bean.print();
        }else{
            System.out.println("Not find BeanOnStartUp");
        }
    }


    @Test
    public void test2(){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BeanDynamic.class);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("beanDynamic",beanDefinitionBuilder.getBeanDefinition());

        BeanDynamic bean = (BeanDynamic) applicationContext.getBean("beanDynamic");

        if(bean!=null){
            bean.print();
        }else{
            System.out.println("Not find BeanDynamic");
        }
    }


    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}