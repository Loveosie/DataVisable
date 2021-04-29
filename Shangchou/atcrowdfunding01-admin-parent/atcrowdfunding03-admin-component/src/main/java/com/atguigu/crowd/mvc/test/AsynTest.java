package com.atguigu.crowd.mvc.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: Shangchou
 * @description: springmvc 异步处理测试
 * @author: lance
 * @create: 2021-01-31 23:21
 */
@Controller
public class AsynTest {
    @ResponseBody
    @RequestMapping("test/ajax/async.html")
    public String asynTest(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
