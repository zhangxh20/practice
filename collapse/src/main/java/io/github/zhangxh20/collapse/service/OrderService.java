package io.github.zhangxh20.collapse.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.github.zhangxh20.collapse.dao.OrderDao;

@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;
    
    public long getAmount() {
        return orderDao.getAmount();
    }
}
