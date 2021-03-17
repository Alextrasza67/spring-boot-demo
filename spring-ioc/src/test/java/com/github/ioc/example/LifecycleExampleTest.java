package com.github.ioc.example;

import com.github.IocApplicationTests;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;


public class LifecycleExampleTest extends IocApplicationTests implements ApplicationContextAware {


    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testLifecycle(){
        System.out.println("testLifecycle......");
        LifecycleExample example = (LifecycleExample) applicationContext.getBean("lifecycleExample");
        example.sout();
        LifecycleExample2 example2 = (LifecycleExample2) applicationContext.getBean("lifecycleExample2");
        System.out.println(example2);
    }

    //BeanFactoryPostProcessor.postProcessBeanFactory
    //LifecycleExampleTest.lifecycleExample
    //无参构造方法
    //BeanNameAware.setBeanName
    //BeanClassLoaderAware.setBeanClassLoader
    //BeanFactoryAware.setBeanFactory
    //ResourceLoaderAware.setResourceLoader
    //ApplicationEventPublisherAware.setApplicationEventPublisher
    //MessageSourceAware.setMessageSource
    //ApplicationContextAware.setApplicationContext
    //BeanPostProcessor.postProcessBeforeInitialization lifecycleExample
    //postConstruct
    //InitializingBean.afterPropertiesSet
    //initMethod
    //BeanPostProcessor.postProcessAfterInitialization lifecycleExample
    //testLifecycle......
    //preDestroy
    //DisposableBean.destroy
    //destoryMethod


    //org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization
    //org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization

}