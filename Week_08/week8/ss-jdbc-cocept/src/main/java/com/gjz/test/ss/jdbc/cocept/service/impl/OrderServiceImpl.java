package com.gjz.test.ss.jdbc.cocept.service.impl;

import com.gjz.test.ss.jdbc.cocept.entity.Order;
import com.gjz.test.ss.jdbc.cocept.entity.OrderExample;
import com.gjz.test.ss.jdbc.cocept.mapper.OrderMapper;
import com.gjz.test.ss.jdbc.cocept.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 订单服务类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/9 16:26
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service
public class OrderServiceImpl  implements OrderService {
    private static final String LOGGER_HEAD = "【 订单服务类 】";
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderMapper orderMapper;

    /**
     * 新增Order
     *
     * @param order
     */
    @Override
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }

    /**
     * 删除订单
     *
     * @param orderId
     */
    @Override
    public void deleteOrder(Long orderId) {
        if (orderId == null) {
            return;
        }
        orderMapper.deleteByPrimaryKey(orderId);
    }

    /**
     * 更新订单
     *
     * @param order
     */
    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKey(order);
    }

    /**
     * 获取订单
     *
     * @param orderId
     * @return
     */
    @Override
    public Order getOrder(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 查询所有订单
     *
     * @return
     */
    @Override
    public List<Order> listOrder() {
        return orderMapper.selectByExample(new OrderExample());
    }
}

