package com.example.demo.ds1.dao;

import com.example.demo.ds1.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("orderDao")
public interface OrderDao {

    void insertOrder(Order order);

}
