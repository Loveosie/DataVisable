package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AssignService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: Shangchou
 * @description: 权限分配控制器
 * @author: lance
 * @create: 2021-02-05 23:37
 */
@Controller
public class AssignHandler {
    @Autowired
    private AssignService assignService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;
    /**
     * 跳转到角色分配页面
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("assign/to/role/page.html")
    public String toAssignPage(@RequestParam(value = "adminId") Integer adminId,
                                             ModelMap modelMap){
        //查询该管理员已分配角色
        List<Role> assignedRoles = roleService.getAssignedRole(adminId);
        List<Role> unAssignedRoles = roleService.getUnassignedRole(adminId);
        modelMap.addAttribute("assignedRoles",assignedRoles);
        modelMap.addAttribute("unAssignedRoles",unAssignedRoles);
        return "assign-role";
    }


    /**
     * 保存admin的角色信息
     */
    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelation(@RequestParam("adminId")Integer adminId,
                                        @RequestParam("pageNum")Integer pageNum,
                                        @RequestParam("keyword")String keyword,
                                        @RequestParam(value = "roleIdList", required=false)List<Integer> roleIdList){
        adminService.saveAdminRoleRelation(adminId,roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
     }

    /**
     * 获取所有权限集合
     * @return
     */
    @RequestMapping("assign/get/all/auth.json")
    @ResponseBody
     public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
     }

    /**
     * 根据roleId 查询id已分配的权限的id集合
     * @return
     */
    @RequestMapping("assign/get/assigned/auth/by/role/id.json")
    @ResponseBody
    public ResultEntity<List<Integer>> getAssignedIdAuthByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> assignedAuthIds = authService.getAssignedIdAuthByRoleId(roleId);
        return ResultEntity.successWithData(assignedAuthIds);
    }


    @RequestMapping("assign/do/role/auth.json")
    @ResponseBody
    public ResultEntity<List<Integer>> doRoleAuthAssign(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuth(map);
        return ResultEntity.successWithoutData();
    }
}

