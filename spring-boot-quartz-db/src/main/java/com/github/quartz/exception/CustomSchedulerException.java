package com.github.quartz.exception;

/**
 * Created by alex on 2018/7/31.
 */
public class CustomSchedulerException extends Exception {

    public CustomSchedulerException(){
        super();
    }

    public CustomSchedulerException(String msg){
        super(msg);
    }

    public CustomSchedulerException(String msg, Throwable cause){
        super(msg,cause);
    }
}
