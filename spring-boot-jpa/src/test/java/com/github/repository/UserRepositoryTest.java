package com.github.repository;

import com.github.SpringDataJpaApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alex on 2018/5/29.
 */
@Transactional
public class UserRepositoryTest extends SpringDataJpaApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        System.out.println(userRepository.count());
    }

}