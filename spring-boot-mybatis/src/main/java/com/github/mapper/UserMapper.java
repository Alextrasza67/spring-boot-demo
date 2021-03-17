package com.github.mapper;

import com.github.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 2018/5/29.
 */
@Repository
public interface UserMapper {

    Long count();

    User get(String id);
}
