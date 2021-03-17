package com.github.service;

import com.github.RedisApplicationTests;
import com.github.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/5/30.
 */
public class UserServiceTest extends RedisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void getWithCache() throws Exception {
        User user = userService.getWithCache("1");
        System.out.println("first : " + user.getLogName());

        Thread.sleep(1000);

        user = userService.getWithCache("1");
        System.out.println("second : " + user.getLogName());
    }

}