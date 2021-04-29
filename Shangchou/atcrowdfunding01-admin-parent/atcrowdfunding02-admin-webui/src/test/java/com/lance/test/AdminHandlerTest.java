package com.lance.test;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-01-27 22:59
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class AdminHandlerTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 插入测试数据
     */
    @Test
    public void test() {
        for(int i = 0; i < 238; i++) {
            adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd"+i, "userName"+i, "email"+i, null));
        }
    }

    @Test
    public void testDS() throws SQLException {
        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }
    Logger logger = LoggerFactory.getLogger(AdminHandlerTest.class);
    @Test
    public  void  testAdminMapper(){
        Admin admin = new Admin(null, "235727", "123456",
                "little tom", "235727@qq.com", null);
        int insert = adminMapper.insert(admin);
        //System.out.println("charuchenggong" + insert);
        logger.info("插入成功{}行",insert);
    }


    @Test
    public void testAdminService(){
        adminService.saveAdmin(new Admin(null, "tom", "123456",
                "little tom", "98989@qq.com", null));

    }
}