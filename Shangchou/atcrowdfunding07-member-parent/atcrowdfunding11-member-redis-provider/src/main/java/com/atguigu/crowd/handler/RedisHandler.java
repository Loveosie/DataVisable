package com.atguigu.crowd.handler;

import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-25 19:34
 */
@RestController
public class RedisHandler {
    @Autowired
    private StringRedisTemplate redisTemplate;

    //http://localhost:3000/set/redis/key/value/remote?key=cat&value=blue
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key")String key,
                                                @RequestParam("value")String value){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key,value);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultEntity.failed(e.getMessage());
        }

    }

    //http://localhost:3000/set/redis/key/value/remote?key=dog&value=pure&time=6000&timeUnit=MINITS
    @RequestMapping("/set/redis/key/value/remote/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(@RequestParam("key")String key,
                                                           @RequestParam("value")String value,
                                                           @RequestParam("time")long time,
                                                           @RequestParam("timeUnit") TimeUnit timeUnit){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value, time, timeUnit);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultEntity.failed(e.getMessage());
        }
    }

    //http://localhost:3000/get/redis/value/by/key?key=dog
    @RequestMapping("/get/redis/value/by/key")
    ResultEntity<String> getRedisValueByKeyRemote(@RequestParam("key") String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            String value = operations.get(key);
            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultEntity.failed(e.getMessage());
        }
    }

    //http://localhost:3000/remove/redis/value/by/key?key=dog
    @RequestMapping("/remove/redis/value/by/key")
    ResultEntity<String> removeRedisValueByKeyRemote(@RequestParam("key") String key){
        try {
            redisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultEntity.failed(e.getMessage());
        }
    }

}
