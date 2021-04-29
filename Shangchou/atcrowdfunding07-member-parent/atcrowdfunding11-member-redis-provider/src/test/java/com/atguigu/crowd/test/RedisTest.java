package com.atguigu.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-24 23:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    // private Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void testSet() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("apple", "red");

    }

    @Test
    public void testExSet() {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("banana","yellow",50000, TimeUnit.MILLISECONDS);

    }

}