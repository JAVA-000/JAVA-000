package com.example.demo.ds1.dao;

import com.example.demo.ds1.model.Order;
import org.springframework.stereotype.Component;

@Component("orderDao")
public interface OrderDao {

    void insertOrder(Order order);

}
