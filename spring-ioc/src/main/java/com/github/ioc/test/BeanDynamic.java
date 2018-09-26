package com.github.ioc.test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alex on 2018/6/12.
 */
public class BeanDynamic {

    @Autowired
    private BeanOnStartUp beanOnStartUp;

    public void print(){
        if(beanOnStartUp!=null)
            beanOnStartUp.print();
        else
            System.out.println("This is BeanDynamic, BeanOnStartUp not injected");
    }
}
