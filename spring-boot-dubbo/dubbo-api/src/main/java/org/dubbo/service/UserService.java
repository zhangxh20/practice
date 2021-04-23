package org.dubbo.service;

import org.dubbo.request.UserReq;
import org.dubbo.response.UserResp;

public interface UserService {

    void register(UserReq user);
    
    UserResp getUserById(int id);
}
