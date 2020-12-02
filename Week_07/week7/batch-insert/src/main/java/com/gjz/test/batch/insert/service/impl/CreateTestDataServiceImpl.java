package com.gjz.test.batch.insert.service.impl;

import com.gjz.test.batch.insert.entity.Order;
import com.gjz.test.batch.insert.service.CreateTestDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * 创建测试数据实现
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/2
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service
public class CreateTestDataServiceImpl implements CreateTestDataService {
    private static final String LOGGER_HEAD = "【 创建测试数据实现 】";

    /**
     * 创建订单测试数据
     *
     * @param size
     * @return
     */
    @Override
    public List<Order> createOrderTestData(Integer size) {
        log.info("{}, createOrderTestData, 创建订单测试数据, 参数：{}" ,LOGGER_HEAD, size);

        Long startTime = System.currentTimeMillis();

        if (size == null || size == 0) {
            return new ArrayList<>();
        }

        List<Order> orders = new ArrayList<>(size);
        Order order;
        for (Integer i = 1; i <= size; i++) {
            order = new Order();
            order.setUserId(Long.valueOf(i));
            order.setOrderSn(UUID.randomUUID().toString());
            order.setUserName("userName" + i);
            order.setTotalAmount(getRandomBigDecimal());
            order.setPayAmount(getRandomBigDecimal());
            order.setFreightAmount(getRandomBigDecimal());
            order.setPayType(i % 3);
            order.setStatus(1);
            order.setAddressId(Long.valueOf(i));
            order.setDeliveryCompany("deliveryCompany" + i);
            order.setDeliverySn("deliverySn" + i);
            order.setRemark("remark" + i);
            order.setConfirmStatus(i % 2);
            order.setDeleteStatus(i % 2);
            order.setPaymentTime(new Date());
            order.setDeliveryTime(new Date());
            order.setReceiveTime(new Date());
            order.setCommentTime(new Date());
            order.setCreateBy("createBy" + i);
            order.setCreateTime(new Date());

            orders.add(order);
        }

        log.info("{}, createOrderTestData, 创建订单测试数据, 耗时：{}" ,LOGGER_HEAD, System.currentTimeMillis() - startTime);
        return orders;
    }

    /**
     * 获取随机BigDecimal
     * @return
     */
    private BigDecimal getRandomBigDecimal(){
        //生成随机数
        BigDecimal db = new BigDecimal(Math.random() * (100 - 100000) + 100);

        //返回保留两位小数的随机数。不进行四舍五入
        return db.setScale(2,BigDecimal.ROUND_DOWN);
    }
}

