package com.example.demo.ds1.dao;

import com.example.demo.config.datasource.dynamic.annotation.DynamicDataSource;
import com.example.demo.ds1.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("orderDao")
public interface OrderDao {

    @DynamicDataSource(name = "ds1")
    void insertOrder(Order order);

}
