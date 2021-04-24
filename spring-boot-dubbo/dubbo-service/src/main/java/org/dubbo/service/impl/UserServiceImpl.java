package org.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.dubbo.dao.UserMapper;
import org.dubbo.entity.User;
import org.dubbo.request.UserReq;
import org.dubbo.response.UserResp;
import org.dubbo.service.UserService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Override
    public void register(UserReq user) {
        User u = new User();
        BeanUtils.copyProperties(user, u);
        userMapper.insert(u);
    }

    @Override
    public UserResp getUserById(int id) {
        User user = userMapper.selectById(id);
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(user, resp);
        return resp;
    }
}
