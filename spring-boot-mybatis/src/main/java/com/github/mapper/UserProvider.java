package com.github.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by alex on 2018/6/13.
 */
public class UserProvider {

    public String getWithResultMapProvider(final String id){
        return new SQL() {{
            SELECT("t.id,t.log_name,t.removed");
            FROM("tb_user t");
            if(id != null){
                WHERE("t.id=#{id}");
            }
        }}.toString();
    }
}
