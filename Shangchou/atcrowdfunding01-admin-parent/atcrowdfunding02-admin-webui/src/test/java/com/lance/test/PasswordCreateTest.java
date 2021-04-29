package com.lance.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-09 23:45
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class PasswordCreateTest {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Test
    public void generator(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
