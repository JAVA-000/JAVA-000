package com.example.demo.ds2.server.impl;

import com.example.demo.ds2.dao.OrderDao;
import com.example.demo.ds2.model.Order;
import com.example.demo.ds2.server.IOrderReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("orderReadService")
public class OrderReadService implements IOrderReadService {

    @Autowired
    @Qualifier("orderReadDao")
    private OrderDao orderDao;

    @Override
    public Order getByOrderId(Long id) {
        return orderDao.findByOrderId(id);
    }
}
