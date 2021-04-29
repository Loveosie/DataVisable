package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-01-28 20:01
 */

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);


    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum, Integer pageSize );

    void deleteAdminById(Integer id);

    Admin getAdminById(Integer id);

    void editAdmin(Admin admin);

    void saveAdminRoleRelation(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
