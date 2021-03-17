package com.github.service;

import com.github.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by alex on 2018/5/30.
 */
@Service
public class UserService {

    @Autowired
    private CacheManager cacheManager;

    private static final String SPRING_BOOT_CACHE = "spring-boot-cache";

    @PostConstruct
    public void test(){
        Cache cache = cacheManager.getCache(SPRING_BOOT_CACHE);
        cache.put("test","aaaa");
        System.out.println(cache.get("test", String.class));
    }



    @Cacheable(value = "spring-boot-cache",keyGenerator = "wiselyKeyGenerator")
    public User getWithCache(String id){
        System.out.println("getting without cache");
        return User.builder().id("id").logName(String.valueOf(System.currentTimeMillis())).build();
    }
}
