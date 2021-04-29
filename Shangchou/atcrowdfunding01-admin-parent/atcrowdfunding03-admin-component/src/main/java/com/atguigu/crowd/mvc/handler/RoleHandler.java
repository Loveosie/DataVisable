package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: Shangchou
 * @description: 角色管理控制器
 * @author: lance
 * @create: 2021-02-01 00:13
 */
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色分页信息
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                                    ModelMap modelMap){
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    /**
     * 保存角色信息
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("role/save.json")
    public  ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }


    
    @RequestMapping("role/update.json")
    @ResponseBody
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 使用requestBody封装前端数组
     * @param ids
     * @return
     */
    @RequestMapping("role/delete/by/role/id/array.json")
    @ResponseBody
    public ResultEntity<String> deleteRoleByIds(@RequestBody List<Integer> ids){
        roleService.removeRole(ids);
        return ResultEntity.successWithoutData();
    }
}
