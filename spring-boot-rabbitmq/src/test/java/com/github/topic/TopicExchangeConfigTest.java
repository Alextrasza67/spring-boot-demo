package com.github.topic;

import com.github.RabbitmqApplicationTests;
import com.github.fanout.FanoutExchangeConfig;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/6/5.
 */
public class TopicExchangeConfigTest extends RabbitmqApplicationTests{

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void test(){
        System.out.println("send a msg to TOPIC_EXCHANGE TOPIC_QUEUE_A.");
        rabbitTemplate.convertAndSend(TopicExchangeConfig.TOPIC_EXCHANGE,TopicExchangeConfig.TOPIC_QUEUE_A,"This is a massage to topic_exchange.");

        /**
         * rabbitmq 默认有个defaultexchange 无需bind 匹配routekey
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send a msg to default_exchange TOPIC_QUEUE_A.");
        rabbitTemplate.convertAndSend(TopicExchangeConfig.TOPIC_QUEUE_A,"This is a massage to default_exchange.");


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send a msg to fanout_exchange.");
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE,"","This is a massage to fanout_exchange.");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send a msg to TOPIC_EXCHANGE TOPIC_QUEUE_B.");
        rabbitTemplate.convertAndSend(TopicExchangeConfig.TOPIC_EXCHANGE,TopicExchangeConfig.TOPIC_QUEUE_B,"This is a massage to topic_exchange.");

//        result is :
//        send a msg to TOPIC_EXCHANGE TOPIC_QUEUE_A.
//        receive msg from TOPIC_QUEUE_A : This is a massage to topic_exchange.
//        receive msg from TOPIC_QUEUE_B : This is a massage to topic_exchange.
//        send a msg to default_exchange TOPIC_QUEUE_A.
//        receive msg from TOPIC_QUEUE_A : This is a massage to default_exchange.
//        send a msg to fanout_exchange.
//                receive msg from FANOUT_QUEUE_A : This is a massage to fanout_exchange.
//        receive msg from TOPIC_QUEUE_B : This is a massage to fanout_exchange.
//        receive msg from FANOUT_QUEUE_B : This is a massage to fanout_exchange.
//        send a msg to TOPIC_EXCHANGE TOPIC_QUEUE_B.
//        receive msg from TOPIC_QUEUE_B : This is a massage to topic_exchange.
    }


}