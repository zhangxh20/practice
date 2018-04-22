package org.dubbo.service;

import org.dubbo.Bootstrap;
import org.dubbo.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Bootstrap.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
    
    public void testRegsiter() {
        User user = new User();
        user.setUsername("caimeizhuang");
        user.setPassword("11111");
        userService.register(user);
    }
    
    @Test
    public void testGetUser() {
        User user = userService.getUserById(14);
        Assert.assertTrue(user.getUsername().equals("caimeizhuang"));
    }
}
