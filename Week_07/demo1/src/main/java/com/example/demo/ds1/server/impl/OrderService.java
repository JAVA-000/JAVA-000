package com.example.demo.ds1.server.impl;

import com.example.demo.ds1.dao.OrderDao;
import com.example.demo.ds1.model.Order;
import com.example.demo.ds1.server.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component("orderService")
public class OrderService implements IOrderService {

    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;

    @Override
    public void saveOrder() {
        Order order = new Order();
        Date now = new Date();
        order.setProductSnapshotId(1L);
        order.setOrderNo("123456");
        order.setTotalFee(new BigDecimal("100"));
        order.setExpressFee(new BigDecimal("20"));
        order.setDiscountFee(BigDecimal.ZERO);
        order.setRealPaymentFee(new BigDecimal("100"));
        order.setPayFlowNo("123456");
        order.setOrderStatus(1);
        order.setOrderCreateTime(now);
        order.setPaymentTime(now);
        order.setExpressTime(now);
        orderDao.insertOrder(order);
    }
}
