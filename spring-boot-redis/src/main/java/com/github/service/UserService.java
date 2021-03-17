package com.github.service;

import com.github.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by alex on 2018/5/30.
 */
@Service
public class UserService {

    @Cacheable(value = "spring-boot-redis",keyGenerator = "wiselyKeyGenerator")
    public User getWithCache(String id){
        System.out.println("getting without cache");
        return User.builder().id("id").logName(String.valueOf(System.currentTimeMillis())).build();
    }
}
