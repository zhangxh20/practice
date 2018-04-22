package io.github.zhangxh20.atomikos.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import io.github.zhangxh20.atomikos.entity.User;

public interface UserMapper {

    @Insert(value="insert user(username,password) value(#{username},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void save(User user);
}
