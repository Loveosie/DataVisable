package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-02-06 10:23
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedIdAuthByRoleId(Integer roleId);

    void saveRoleAuth(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
