package com.example.demo.ds1.dao;

import com.example.demo.config.datasource.dynamic.annotation.DynamicDataSource;
import com.example.demo.ds1.model.Order;
import org.springframework.stereotype.Component;

@Component("orderDao")
public interface OrderDao {

    @DynamicDataSource(name = "ds1")
    void insertOrder(Order order);

}
