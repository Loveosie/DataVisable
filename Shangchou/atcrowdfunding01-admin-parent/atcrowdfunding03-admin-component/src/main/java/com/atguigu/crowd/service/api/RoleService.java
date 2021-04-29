package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: Shangchou
 * @description:  角色业务类
 * @author: lance
 * @create: 2021-01-28 20:01
 */

public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIds);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnassignedRole(Integer adminId);
}
