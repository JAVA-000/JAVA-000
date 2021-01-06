package com.example.demo.ds1.dao;

import com.example.demo.ds1.model.Order;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface OrderDao {

    void insertOrder(Order order);

}
