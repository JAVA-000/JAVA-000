package com.gjz.test.batch.insert.service;

import com.gjz.test.batch.insert.entity.Order;

import java.util.List;

/**
 * <pre>
 * 生成测试数据Service
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
public interface CreateTestDataService {

    /**
     * 创建订单测试数据
     * @param size
     * @return
     */
    List<Order> createOrderTestData(Integer size);
}
