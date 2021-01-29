package com.example.demo.ds1.controller;

import com.example.demo.ds1.model.Order;
import com.example.demo.ds1.server.IOrderService;
import com.example.demo.ds1.server.ISchoolService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zenglh
 * @date 2020/12/9 22:16
 */
@RestController("orderController")
@RequestMapping("/order")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
    public void getOrderById() {
        orderService.saveOrder();
    }

}
