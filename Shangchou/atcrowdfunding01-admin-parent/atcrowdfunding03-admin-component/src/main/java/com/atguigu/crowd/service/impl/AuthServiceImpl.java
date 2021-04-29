package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.AuthExample;
import com.atguigu.crowd.mapper.AuthMapper;
import com.atguigu.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-02-06 10:23
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Integer> getAssignedIdAuthByRoleId(Integer roleId) {
        return authMapper.getAssignedIdAuthByRoleId(roleId);
    }

    @Override
    public void saveRoleAuth(Map<String, List<Integer>> map) {
        //获取roleId, 删除旧的权限
        List<Integer> roleIds = map.get("roleId");
        Integer roleId = roleIds.get(0);
        authMapper.deleteRelationshipByRoleId(roleId);

        //获取authIdList
        List<Integer> authIdArray = map.get("authIdArray");
        if (authIdArray != null && authIdArray.size() > 0){
            authMapper.saveRoleAuthRelationship(roleId,authIdArray);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }
}
