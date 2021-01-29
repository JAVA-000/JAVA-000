package com.example.demo.ds2.controller;

import com.example.demo.ds2.model.Order;
import com.example.demo.ds2.server.IOrderReadService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zenglh
 * @date 2020/12/9 22:16
 */
@RestController("orderReadController")
@RequestMapping("/orderRead")
public class OrderController {

    @Resource(name = "orderReadService")
    private IOrderReadService orderService;

    @RequestMapping(value = "/getOrderById", method = RequestMethod.GET)
    public Order getOrderById(@RequestParam("id") Long id) {
        return orderService.getByOrderId(id);
    }

}
