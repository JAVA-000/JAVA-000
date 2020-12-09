package com.gjz.test.ss.jdbc.cocept.service;

import com.gjz.test.ss.jdbc.cocept.entity.Order;

import java.util.List;

/**
 * <pre>
 * 订单服务类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/9 16:25
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface OrderService {

    /**
     * 新增Order
     * @param order
     */
    void createOrder(Order order);

    /**
     * 删除订单
     * @param orderId
     */
    void deleteOrder(Long orderId);

    /**
     * 更新订单
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    Order getOrder(Long orderId);

    /**
     * 查询所有订单
     * @return
     */
    List<Order> listOrder();
}
