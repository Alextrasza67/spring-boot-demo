package com.github.mapper;

import com.github.SpringBootMybatisApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/5/29.
 */
@Transactional
public class UserMapperTest extends SpringBootMybatisApplicationTests{

    @Autowired
    private UserMapper userMapper;

    @Test
    public void count(){
        assertEquals(userMapper.count(),new Long("1"));
    }

    @Test
    public void get(){
        assertEquals(userMapper.get("1").getLogName(),"test");
    }

}