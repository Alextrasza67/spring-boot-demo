package com.github.ioc.test2;

import com.github.IocApplicationTests;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by alex on 2018/6/12.
 */
public class BeanAfterStartUpTest extends IocApplicationTests implements ApplicationContextAware{

    @Test
    public void test(){
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BeanAfterStartUp.class);
        defaultListableBeanFactory.registerBeanDefinition("beanAfterStartUp",beanDefinitionBuilder.getBeanDefinition());
        BeanDefinitionBuilder beanDefinitionBuilder2 = BeanDefinitionBuilder.genericBeanDefinition(BeanDynamic.class);
        defaultListableBeanFactory.registerBeanDefinition("beanDynamic",beanDefinitionBuilder2.getBeanDefinition());

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