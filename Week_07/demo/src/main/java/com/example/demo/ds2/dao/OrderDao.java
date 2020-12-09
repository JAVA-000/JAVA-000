package com.example.demo.ds2.dao;

import com.example.demo.ds2.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("orderReadDao")
public interface OrderDao {

    Order findByOrderId(@Param("id") Long id);

}
