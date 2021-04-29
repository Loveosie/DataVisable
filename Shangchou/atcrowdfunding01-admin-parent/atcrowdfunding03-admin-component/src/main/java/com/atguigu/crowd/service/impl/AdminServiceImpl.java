package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.exception.EditAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: Shangchou
 * @description:
 * @author: lance
 * @create: 2021-01-28 20:01
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //根据登录账号查询Admin对象
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        if (admins == null || admins.size() == 0) {
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (admins.size() > 1) {
            throw  new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        //判断admin是否为null
        if (admins.get(0) == null ){
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        //不为null则获取admin密码
        Admin admin = admins.get(0);

        //比较数据库密码和加密后的明文密码
        String userPwdFromPage = CrowdUtil.md5Encode(userPswd);
        String userPwdFromDB= admin.getUserPswd();
        if (!Objects.equals(userPwdFromPage, userPwdFromDB)) {
            throw  new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //if (!encodePwd.equals(admin.getUserPswd())){
        //    //不一致抛异常
        //    throw  new LoginFailedException("用户密码错误");
        //}

        //一致返回admin
        return admin;
    }

    @Override
    public void editAdmin(Admin admin) {

        try {
            //有选择的更新; id为null的值不更新
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常全类名" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new EditAcctAlreadyInUseException(CrowdConstant.MESSAGE_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelation(Integer adminId, List<Integer> roleIdList) {
        //根据adminId删除旧的关系
        adminMapper.deleteRelationShip(adminId);
        //根据adminId和roleIdList创建新的关系
        if (roleIdList != null && roleIdList.size()>0){
            adminMapper.createRelationShip(adminId,roleIdList);
        }

    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(username);
        return adminMapper.selectByExample(adminExample).get(0);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id删除admin
     * @param id
     */
    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    /**
     * 将使用PageHelper查询到的数据返回
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //调用pagehelper, 非侵入式设计, 不用修改原有SQL
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admins = adminMapper.selectAdminByKeyWord(keyword);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        return pageInfo;
    }

    @Override
    public List<Admin> getAll() {

        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        //userPswd = CrowdUtil.md5Encode(userPswd);

        //使用springsecurity 对用户密码进行加密
        userPswd = passwordEncoder.encode(userPswd);
        Date date = new Date();
        String createTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        admin.setCreateTime(createTime);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常全类名" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_ACCT_ALREADY_IN_USE);
            }
        }

    }
}
