package com.gjz.test.batch.insert.service.impl;

import com.gjz.test.batch.insert.entity.Order;
import com.gjz.test.batch.insert.service.BatchInsertService;
import com.gjz.test.batch.insert.service.CreateTestDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <pre>
 * 批量创建数据
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/2 19:15
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service
public class BatchInsertServiceImpl implements BatchInsertService {
    private static final String LOGGER_HEAD = "【 批量创建数据 】";

    @Resource
    CreateTestDataService createTestDataService;

    @Autowired
    JdbcTemplate jdbcTemplate;


    private final String TABLE_NAME = "t_order";

    private final String FIELD_LIST = "(user_id, order_sn, user_name, total_amount, pay_amount, freight_amount, pay_type, status, " +
            "address_id, delivery_company, delivery_sn, remark, confirm_status, delete_status, payment_time, delivery_time, " +
            "receive_time, comment_time, create_by, create_time)";
    /**
     * 批量插入数据，批量记录
     */
    @Override
    public void batchInsertBatchRecord() {
        log.info("{}, batchInsertBatchRecord, 使用jdbcTemplate batchUpdate, 批量插入数据" ,LOGGER_HEAD);
        List<Order> testData = createTestDataService.createOrderTestData(100);

        long startTime = System.currentTimeMillis();

        long dateSize = 0;
        for (int i = 0; i < 100; i++) {
            dateSize += testData.size();
            batchInsertOrderData(testData);
        }

        log.info("{}, batchInsertBatchRecord, 使用jdbcTemplate batchUpdate, 批量插入数据, 数据大小：{}, 耗时：{}" ,LOGGER_HEAD, dateSize, System.currentTimeMillis() - startTime);
    }



    /**
     * 批量插入，内容直接拼在sql上
     */
    @Override
    public void batchInsertOrderDataMoreValues(){
        log.info("{}, batchInsertOrderDataMoreValues, 使用insert into values(),(), 批量插入数据" ,LOGGER_HEAD);
        List<Order> testData = createTestDataService.createOrderTestData(1000);

        long startTime = System.currentTimeMillis();

        long dateSize = 0;
        for (int i = 0; i < 1000; i++) {
            dateSize += testData.size();
            batchInsertOrderDataMoreValues(testData);
        }

        log.info("{}, batchInsertOrderDataMoreValues, 使用insert into values(),(), 批量插入数据, 数据大小：{}, 耗时：{}" ,LOGGER_HEAD, dateSize, System.currentTimeMillis() - startTime);

    }


    /**
     * 批量插入订单数据
     * @param testData
     */
    private void batchInsertOrderData(List<Order> testData) {

        String sql = "insert into " + TABLE_NAME + FIELD_LIST +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1,testData.get(i).getUserId());
                ps.setString(2,testData.get(i).getOrderSn());
                ps.setString(3,testData.get(i).getUserName());
                ps.setBigDecimal(4,testData.get(i).getTotalAmount());
                ps.setBigDecimal(5,testData.get(i).getPayAmount());
                ps.setBigDecimal(6,testData.get(i).getFreightAmount());
                ps.setInt(7,testData.get(i).getPayType());
                ps.setInt(8,testData.get(i).getStatus());
                ps.setLong(9,testData.get(i).getAddressId());
                ps.setString(10,testData.get(i).getDeliveryCompany());
                ps.setString(11, testData.get(i).getDeliverySn());
                ps.setString(12, testData.get(i).getRemark());
                ps.setInt(13,testData.get(i).getConfirmStatus());
                ps.setInt(14,testData.get(i).getDeleteStatus());
                ps.setDate(15,new Date(testData.get(i).getPaymentTime().getTime()));
                ps.setDate(16, new Date(testData.get(i).getDeliveryTime().getTime()));
                ps.setDate(17,new Date(testData.get(i).getReceiveTime().getTime()));
                ps.setDate(18, new Date(testData.get(i).getCommentTime().getTime()));
                ps.setString(19, testData.get(i).getCreateBy());
                ps.setDate(20, new Date(testData.get(i).getCreateTime().getTime()));
            }

            @Override
            public int getBatchSize() {
                return testData.size();
            }
        });
    }

        /**
         * 批量插入订单数据
         * @param testData
         */
    private void batchInsertOrderDataMoreValues(List<Order> testData) {

        StringBuilder builder = new StringBuilder();
        builder.append("insert into ")
                .append(TABLE_NAME)
                .append(FIELD_LIST)
                .append("values");

        for (Order order : testData) {
            builder.append(orderToString(order)).append(",");
        }
        // 把插入内容拼接在Sql上
        String sql = builder.deleteCharAt(builder.length() - 1).toString();

        jdbcTemplate.execute(sql);
    }

    /**
     * order 转 String
     * @param order
     * @return
     */
    private String orderToString(Order order){
        StringBuilder builder = new StringBuilder();
        builder.append("(")
        .append("'").append(order.getUserId()).append("'").append(",")
        .append("'").append(order.getOrderSn()).append("'").append(",")
        .append("'").append(order.getUserName()).append("'").append(",")
        .append("'").append(order.getTotalAmount()).append("'").append(",")
        .append("'").append(order.getPayAmount()).append("'").append(",")
        .append("'").append(order.getFreightAmount()).append("'").append(",")
        .append("'").append(order.getPayType()).append("'").append(",")
        .append("'").append(order.getStatus()).append("'").append(",")
        .append("'").append(order.getAddressId()).append("'").append(",")
        .append("'").append(order.getDeliveryCompany()).append("'").append(",")
        .append("'").append(order.getDeliverySn()).append("'").append(",")
        .append("'").append(order.getRemark()).append("'").append(",")
        .append("'").append(order.getConfirmStatus()).append("'").append(",")
        .append("'").append(order.getDeleteStatus()).append("'").append(",")
        .append("'").append(dateToStr(order.getPaymentTime())).append("'").append(",")
        .append("'").append(dateToStr(order.getDeliveryTime())).append("'").append(",")
        .append("'").append(dateToStr(order.getReceiveTime())).append("'").append(",")
        .append("'").append(dateToStr(order.getCommentTime())).append("'").append(",")
        .append("'").append(order.getCreateBy()).append("'").append(",")
        .append("'").append(dateToStr(order.getCreateTime())).append("'")
        .append(")");

        return builder.toString();
    }

    /**
     * 时间转换
     * @param date
     * @return
     */
    private String dateToStr(java.util.Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }



}

