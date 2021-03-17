package com.github.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alex on 2018/6/4.
 */

/**
 * 广播
 */
@Configuration
public class FanoutExchangeConfig {

    public final static String FANOUT_QUEUE_A = "fanout.queue.A";
    public final static String FANOUT_QUEUE_B = "fanout.queue.B";
    public final static String FANOUT_QUEUE_C = "fanout.queue.C";

    public final static String FANOUT_EXCHANGE = "fanout_exchange";
    public final static String FANOUT_EXCHANGE_2 = "fanout_exchange_2";

    @Bean
    public Queue queueA() {
        return new Queue(FANOUT_QUEUE_A);
    }

    @Bean
    public Queue queueB() {
        return new Queue(FANOUT_QUEUE_B);
    }

    @Bean
    public Queue queueC() {
        return new Queue(FANOUT_QUEUE_C);
    }

    @Bean//("fanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean//("fanoutExchange2")
    public FanoutExchange fanoutExchange2() {
        return new FanoutExchange(FANOUT_EXCHANGE_2);
    }

    @Bean
    Binding bindQueueA(){
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindQueueB(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindQueueB2(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange2());
    }

    @Bean
    Binding bindQueueC(){
        return BindingBuilder.bind(queueC()).to(fanoutExchange2());
    }

    @RabbitHandler
    @RabbitListener(queues = FANOUT_QUEUE_A)
    public void queueAProcess(String message){
        System.out.println("receive msg from FANOUT_QUEUE_A : "+message);
    }

    @RabbitHandler
    @RabbitListener(queues = FANOUT_QUEUE_B)
    public void queueBProcess(String message){
        System.out.println("receive msg from FANOUT_QUEUE_B : "+message);
    }

    @RabbitHandler
    @RabbitListener(queues = FANOUT_QUEUE_C)
    public void queueCProcess(String message){
        System.out.println("receive msg from FANOUT_QUEUE_C : "+message);
    }
}
