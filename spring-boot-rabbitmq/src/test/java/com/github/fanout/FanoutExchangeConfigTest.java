package com.github.fanout;

import com.github.RabbitmqApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/6/4.
 */
public class FanoutExchangeConfigTest extends RabbitmqApplicationTests{

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void test(){
        System.out.println("send a msg to fanout_exchange.");
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE,"","This is a massage to fanout_exchange.");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send a msg to fanout_exchange_2.");
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE_2,"","This is a massage to fanout_exchange_2.");

//        result is :
//        send a msg to fanout_exchange.
//        receive msg from TOPIC_QUEUE_B : This is a massage to fanout_exchange.
//        receive msg from FANOUT_QUEUE_B : This is a massage to fanout_exchange.
//        receive msg from FANOUT_QUEUE_A : This is a massage to fanout_exchange.
//        send a msg to fanout_exchange_2.
//        receive msg from FANOUT_QUEUE_B : This is a massage to fanout_exchange_2.
//        receive msg from FANOUT_QUEUE_C : This is a massage to fanout_exchange_2.

    }

}