package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @program: Shangchou
 * @description: 管理员控制器
 * @author: lance
 * @create: 2021-01-30 00:13
 */
@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;

    /**
     * 去到登录页面, 根据表单信息查询管理员信息放入session
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct")String loginAcct,
                          @RequestParam("userPswd")String userPswd,
                          HttpSession session){
        //正确,登录; 错误, 返回登录页面(CrowdExceptionResolver.resolveLoginFieldException())
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        //直接 return "admin-main"; 会重复提交, IO性能较低
        return "redirect:/admin/to/main/page.html";
    }

    /**
     * 登出跳转到登陆页面, session失效
     */
    @RequestMapping("admin/do/logout.html")
    public  String doLogout(HttpSession session){
        session.invalidate();
        return  "redirect:/admin/to/login/page.html";
    }

    /**
     * 传入关键字进行查询, 将结果进行分页处理, 将得到的pageHelper放入请求域中
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                              ModelMap modelMap){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    /**
     * 删除某id管理员, 回到分页页面
     *  admin/delete/${admin.id}/${pageInfo.pageNum}/${param.keyword}.html"
     * @param id
     * @return
     */
    @RequestMapping("/admin/delete/{adminId}/{pageNum}/{keyword}.html")
    public String deleteAdminById(@PathVariable("adminId") Integer id,
                                  @PathVariable("pageNum") Integer pageNum,
                                  @PathVariable("keyword")String keyword){
        adminService.deleteAdminById(id);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/addAdmin.html")
    public String addAdmin(Admin admin){
        adminService.saveAdmin(admin);
        return  "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer id,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword")String keyword,
                             ModelMap modelMap){
        Admin admin = adminService.getAdminById(id);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_ADMIN,admin);
        return "admin-edit";

    }

    @RequestMapping("admin/editAdmin.html")
    public  String editAdmin(Admin admin,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword")String keyword){
        adminService.editAdmin(admin);
        return  "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
