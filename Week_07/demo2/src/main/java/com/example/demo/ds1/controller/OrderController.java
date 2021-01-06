package com.example.demo.ds1.controller;

import com.example.demo.ds1.server.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zenglh
 * @date 2020/12/9 22:16
 */
@RestController("orderController")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
    public void getOrderById() {
        orderService.saveOrder();
    }

}
