package com.github.ioc.test2;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alex on 2018/6/12.
 */
public class BeanDynamic {

    @Autowired(required = false)
    private BeanAfterStartUp beanAfterStartUp;

    public void print(){
        if(beanAfterStartUp!=null)
            beanAfterStartUp.print();
        else
            System.out.println("This is BeanDynamic, BeanOnStartUp not injected");
    }
}
