package com.github.mapper;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class CustomQueryProvider {

    public String customQuery(Map<String, Object> params){
        return (String) params.get("sql");
    }
}
