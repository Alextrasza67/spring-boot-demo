package com.github.ioc.test;

import org.springframework.stereotype.Component;

/**
 * Created by alex on 2018/6/12.
 */
@Component
public class BeanOnStartUp {

    public void print(){
        System.out.println("This bean is BeanOnStartUp!");
    }
}
