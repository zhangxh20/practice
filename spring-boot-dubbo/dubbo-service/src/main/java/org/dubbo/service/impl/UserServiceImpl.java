package org.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.dubbo.dao.UserMapper;
import org.dubbo.request.UserReq;
import org.dubbo.response.UserResp;
import org.dubbo.service.UserService;

import javax.annotation.Resource;

@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Override
    public void register(UserReq user) {

    }

    @Override
    public UserResp getUserById(int id) {
        return null;
    }
}
