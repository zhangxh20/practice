package org.dubbo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dubbo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
