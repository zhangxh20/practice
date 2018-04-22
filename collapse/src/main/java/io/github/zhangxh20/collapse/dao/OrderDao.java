package io.github.zhangxh20.collapse.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    
    public long getAmount() {
        String sql = "select sum(amount) from user where code = 'zhang'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
