package com.example.demo.ds1.dao;

import com.example.demo.ds1.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {

    Order findByOrderId(@Param("id") Long id);

}
