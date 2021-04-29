package com.atguigu.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-25 20:49
 */
@Controller
public class PortalHandler {
    @RequestMapping("/")
    public String showPortalPage() {
        // 这里实际开发中需要加载数据……
        return "portal";
    }
}
