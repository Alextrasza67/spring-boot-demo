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
public class UserMapperAnnotationTest extends SpringBootMybatisApplicationTests{

    @Autowired
    private UserMapperAnnotation userMapperAnnotation;

    @Test
    public void count() throws Exception {
        assertEquals(userMapperAnnotation.count(),new Long("1"));
    }

    @Test
    public void get(){
        assertEquals(userMapperAnnotation.get("1").getLogName(),"test");
    }

    @Test
    public void getWithResultMap(){
        assertEquals(userMapperAnnotation.getWithResultMap("1").getLogName(),"test");
    }

    @Test
    public void getWithResultMapWithProvider(){
        assertEquals(userMapperAnnotation.getWithResultMapWithProvider("1").getLogName(),"test");
    }

}