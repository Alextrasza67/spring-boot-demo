package com.github.topic;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alex on 2018/6/5.
 */
@Configuration
public class TopicExchangeConfig {


    public final static String TOPIC_QUEUE_A = "topic.queue.a";
    public final static String TOPIC_QUEUE_A_ROUTE_KEY = "topic.queue.a";
    public final static String TOPIC_QUEUE_B = "topic.queue.b";
    public final static String TOPIC_QUEUE_B_ROUTE_KEY = "topic.queue.*";

    public final static String TOPIC_EXCHANGE = "topic_exchange";


    @Bean
    public Queue topicQueueA() {
        return new Queue(TOPIC_QUEUE_A);
    }

    @Bean
    public Queue topicQueueB() {
        return new Queue(TOPIC_QUEUE_B);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

//    @Autowired
//    @Qualifier("fanoutExchange")
//    private FanoutExchange fanoutExchange;


    @Bean
    Binding bindTopicQueueA(){
        return BindingBuilder.bind(topicQueueA()).to(topicExchange()).with(TOPIC_QUEUE_A_ROUTE_KEY);
    }
    @Bean
    Binding bindTopicQueueB(){
        return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with(TOPIC_QUEUE_B_ROUTE_KEY);
    }
    @Bean
    Binding bindTopicQueueBToFanoutExchange(FanoutExchange fanoutExchange){
        return BindingBuilder.bind(topicQueueB()).to(fanoutExchange);
    }

    @RabbitHandler
    @RabbitListener(queues = TOPIC_QUEUE_A)
    public void topicQueueAProcess(String message){
        System.out.println("receive msg from TOPIC_QUEUE_A : "+message);
    }

    @RabbitHandler
    @RabbitListener(queues = TOPIC_QUEUE_B)
    public void topicQueueBProcess(String message){
        System.out.println("receive msg from TOPIC_QUEUE_B : "+message);
    }

}
