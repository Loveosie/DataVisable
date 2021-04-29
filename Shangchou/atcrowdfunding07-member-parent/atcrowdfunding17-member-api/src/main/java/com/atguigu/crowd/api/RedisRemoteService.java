package com.atguigu.crowd.api;

import com.atguigu.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-24 22:39
 */
@FeignClient("atguigu-crowd-mysql")
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key")String key,
                                                @RequestParam("value")String value);
    /**
     * 带redis失效时间
     * */
    @RequestMapping("/set/redis/key/value/remote/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(@RequestParam("key")String key,
                                                @RequestParam("value")String value,
                                                @RequestParam("time")long time,

                                                @RequestParam("timeUnit")TimeUnit timeUnit);

    @RequestMapping("/get/redis/value/by/key")
    ResultEntity<String> getRedisValueByKeyRemote(@RequestParam("key") String key);

    @RequestMapping("/remove/redis/value/by/key")
    ResultEntity<String> removeRedisValueByKeyRemote(@RequestParam("key") String key);

}