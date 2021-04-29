package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.po.MemberPO;

/**
*@program: pro05-spring-security
*@description:
*@author: lance
*@create: 2021-02-24 22:52
*/
public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
