package org.dubbo.dao;

import javax.annotation.Resource;

import org.dubbo.bean.User;
import org.junit.Assert;
import org.junit.Test;

public class UserMapperTest extends AbstractMapperTest {

    @Resource
    private UserMapper userMapper;
    
    @Test
    public void test1() {
        User user = userMapper.get(1);
        System.out.println(user);
        Assert.assertNotNull(user);
    }
}
