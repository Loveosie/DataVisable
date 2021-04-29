package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: pro05-spring-security
 * @description:
 * @author: lance
 * @create: 2021-02-24 22:52
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        // 1.创建 Example 对象
        MemberPOExample example = new MemberPOExample();
        // 2.创建 Criteria 对象
        MemberPOExample.Criteria criteria = example.createCriteria();
        // 3.封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        // 4.执行查询
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        // 5.获取结果
        return list.get(0);

    }
}
