package com.lance.test;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-02-01 00:38
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class RoleHandlerTest {
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void insertData(){
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null,"role"+i));
        }
    }
}
