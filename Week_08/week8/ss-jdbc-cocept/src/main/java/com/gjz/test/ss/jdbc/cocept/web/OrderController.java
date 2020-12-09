package com.gjz.test.ss.jdbc.cocept.web;

import com.gjz.test.ss.jdbc.cocept.entity.Order;
import com.gjz.test.ss.jdbc.cocept.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * 订单服务类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/9 16:24
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/ss/jdbc/cocept")
public class OrderController {

    @Resource
    OrderService orderService;

    /**
     * 新增Order
     * @param order
     */
    @PostMapping(value = "/create")
    void createOrder(@RequestBody Order order){
        orderService.createOrder(order);
    }

    /**
     * 删除订单
     * @param orderId
     */
    @DeleteMapping("/delete")
    void deleteOrder(Long orderId){
        orderService.deleteOrder(orderId);
    }

    /**
     * 更新订单
     * @param order
     */
    @PostMapping("/update")
    void updateOrder(Order order){
        orderService.updateOrder(order);
    }

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    @GetMapping("/get")
    Order getOrder(Long orderId){
        return orderService.getOrder(orderId);
    }

    /**
     * 查询所有订单
     * @return
     */
    @GetMapping("/list")
    List<Order> listOrder(){
        return orderService.listOrder();
    }
}

