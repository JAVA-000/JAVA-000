package com.example.demo.ds2.dao;

import com.example.demo.ds2.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("orderReadDao")
public interface OrderDao {

    Order findByOrderId(@Param("id") Long id);

}
