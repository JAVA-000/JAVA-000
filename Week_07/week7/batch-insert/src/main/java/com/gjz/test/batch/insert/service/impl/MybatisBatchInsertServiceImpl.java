package com.gjz.test.batch.insert.service.impl;

import com.gjz.test.batch.insert.entity.Order;
import com.gjz.test.batch.insert.mapper.OrderMapper;
import com.gjz.test.batch.insert.service.CreateTestDataService;
import com.gjz.test.batch.insert.service.MybatisBatchInsertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * 使用Mybatis简化批量插入数据代码
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
public class MybatisBatchInsertServiceImpl implements MybatisBatchInsertService {
    private static final String LOGGER_HEAD = "【 使用Mybatis简化批量插入数据代码 】";


    @Resource
    CreateTestDataService createTestDataService;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 批量插入订单数据
     */
    @Override
    public void batchInsertOrderData() {
        List<Order> orders = createTestDataService.createOrderTestData(1000);
        log.info("{}, batchInsertOrderData, 使用Mybatis 简化代码" ,LOGGER_HEAD);

        long startTime = System.currentTimeMillis();
        long dataSize = 0;
        for (int i = 0; i < 1000; i++) {
            dataSize += orders.size();
            orderMapper.batchInsertOrder(orders);
        }

        log.info("batchInsertOrderData,批量插入订单数据, 记录数：{}, 耗时：{}", dataSize, (System.currentTimeMillis() - startTime));
    }
}

