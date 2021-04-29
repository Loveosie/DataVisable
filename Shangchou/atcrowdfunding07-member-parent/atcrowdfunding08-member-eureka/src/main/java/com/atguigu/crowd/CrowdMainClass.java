package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-24 19:12
 */
@SpringBootApplication
@EnableEurekaServer
public class CrowdMainClass {
    public static void main(String[] args) {
        SpringApplication.run(CrowdMainClass.class, args);

    }
}
