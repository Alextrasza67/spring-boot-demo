package com.github.redis;

import com.github.RedisApplicationTests;
import com.github.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by alex on 2018/5/29.
 */
public class ClientTest extends RedisApplicationTests{

    @Autowired
    private Client client;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test() throws Exception {
//        client.set("aaa","bbb");
//        assertEquals(client.get("aaa"),"bbb");
//
//        User user = User.builder().id("1").logName("aaa").build();
//        client.set("user", user);
//
//        User userInRedis = (User) client.get("user");
//        assertEquals(userInRedis.getLogName(),"aaa");
    }

    @Test
    public void flushDB(){
        client.flushDB();
    }

    @Test
    public void testRedisTemplateForList(){
        ListOperations<String,Object> lo = redisTemplate.opsForList();

        lo.trim("list", 1,0);

        lo.leftPush("list","1");
        lo.leftPush("list","2");
        lo.rightPush("list","3");

        System.out.println(lo.size("list"));
        System.out.println(lo.rightPop("list"));
        System.out.println(lo.size("list"));

    }

    @Test
    public void testRedisTemplateForHash(){
        HashOperations<String,String,Object> ho = redisTemplate.opsForHash();


        ho.put("hash","1","test1");
        ho.put("hash","2","test2");
        ho.put("hash2","1","test1");

        System.out.println(ho.size("hash"));

        System.out.println(ho.get("hash","1"));
        System.out.println(ho.hasKey("hash2","1"));

        ho.delete("hash","2");
        System.out.println(ho.size("hash"));


        redisTemplate.delete("hash");
        System.out.println(ho.size("hash"));
    }

    @Test
    public void testRedisTemplate(){
        Set keys = redisTemplate.keys("*");
        System.out.println(Arrays.toString(keys.toArray()));
    }


}