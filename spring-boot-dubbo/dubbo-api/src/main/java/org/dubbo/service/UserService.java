package org.dubbo.service;

import org.dubbo.bean.User;

public interface UserService {

    void register(User user);
    
    User getUserById(int id);
}
