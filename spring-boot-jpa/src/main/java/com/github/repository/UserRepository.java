package com.github.repository;

import com.github.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 2018/5/29.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor {
}
