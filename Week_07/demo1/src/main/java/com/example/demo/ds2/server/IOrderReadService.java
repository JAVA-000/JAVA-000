package com.example.demo.ds2.server;

import com.example.demo.ds2.model.Order;

public interface IOrderReadService {


    Order getByOrderId(Long id);

}
