package com.lance.test;

import com.atguigu.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class StringTest {


    @Test
    public void testMD5(){
        String source = "123456";
        String s = CrowdUtil.md5Encode(source);
        System.out.println(s);
    }

}