package io.github.zhangxh20.atomikos.dao.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import io.github.zhangxh20.atomikos.entity.User;

public interface MemberMapper {

    @Insert("insert into group_member(groupId,userId) values(#{groupId},#{userId})")
    void create(User user);
    
    @Select("select count(1) from group_member where userId = #{userId}")
    int count(int userId);
}
