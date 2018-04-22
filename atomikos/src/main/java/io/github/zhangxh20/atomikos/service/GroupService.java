package io.github.zhangxh20.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.zhangxh20.atomikos.dao.member.MemberMapper;
import io.github.zhangxh20.atomikos.dao.user.UserMapper;
import io.github.zhangxh20.atomikos.entity.User;

@Service
public class GroupService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Transactional(rollbackFor=Exception.class)
    public void create() throws Exception {
        User user = new User();
        user.setUsername("rollback");
        user.setPassword("rollback");
        user.setGroupId(1);
        userMapper.save(user);
        int count = memberMapper.count(user.getUserId());
        if (count > 0) {
            throw new Exception("count > 0");
        } else {
            memberMapper.create(user);
        }
    }
}
