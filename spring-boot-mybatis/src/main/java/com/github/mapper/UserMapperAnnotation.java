package com.github.mapper;

import com.github.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 2018/5/29.
 */
@Repository
public interface UserMapperAnnotation {

    /**
     * annotation
     * @return
     */
    @Select("select count(t.id) from tb_user t")
    Long count();

    /**
     * annotation
     * 返回值映射实体类使用 {@link Result}
     * @param id
     * @return
     */
    @Select("select t.id,t.log_name,t.removed from tb_user t where t.id=#{id}")
    @Results(id="UserMap", value = {
            @Result(column = "id",property = "id"),
            @Result(column = "log_name",property = "logName"),
            @Result(column = "removed",property = "removed")
    })
    User get(String id);

    /**
     * annotation 与xml复用
     * 使用 xml 定义返回映射
     * @param id
     * @return
     */
    @Select("select t.id,t.log_name,t.removed from tb_user t where t.id=#{id}")
    @ResultMap("com.github.mapper.UserMapper.User")
    User getWithResultMap(String id);

    /**
     * provider
     * 使用 provider 定义返回映射
     * @param id
     * @return
     */
    @SelectProvider(type=UserProvider.class,method = "getWithResultMapProvider")
    @ResultMap("UserMap")
//    @ResultMap("com.github.mapper.UserMapper.User")
    User getWithResultMapWithProvider(String id);

}
