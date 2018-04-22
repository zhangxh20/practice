package org.dubbo.service.impl;

import org.dubbo.bean.User;
import org.dubbo.dao.UserMapper;
import org.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass=UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Transactional
    @Override
    public void register(User user) {
        userMapper.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.get(id);
    }

}
