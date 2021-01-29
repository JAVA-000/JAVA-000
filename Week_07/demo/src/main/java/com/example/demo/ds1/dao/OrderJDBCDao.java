package com.example.demo.ds1.dao;

import com.example.demo.util.JDBCUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//@Component
public class OrderJDBCDao {

    public void insertBatchOrder() {
        Connection connection = JDBCUtils.getConnection();
        String sql = "INSERT INTO zlh_order " +
                "(id,product_snapshot_id,order_no,total_fee,express_fee,discount_fee,real_payment_fee," +
                "pay_flow_no,order_status,order_create_time,payment_time,express_time,deleted) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = JDBCUtils.getPreparedStatement(connection, sql);
        try {
            connection.setAutoCommit(false);
            int sum = 0;
            BigDecimal bigDecimal;
            BigDecimal expressFee = new BigDecimal("20");
            long startTime = System.currentTimeMillis();
            Date now = new Date(startTime);
            for (int i = 1; i <= 100; i++) {
                for (int j = 1; j <= 10000; j++) {
                    sum += j;
                    bigDecimal = new BigDecimal(String.valueOf(sum));
                    preparedStatement.setLong(1, sum);
                    preparedStatement.setLong(2, sum);
                    preparedStatement.setString(3, "0000" + sum);
                    preparedStatement.setBigDecimal(4, bigDecimal);
                    preparedStatement.setBigDecimal(5, expressFee);
                    preparedStatement.setBigDecimal(6, bigDecimal);
                    preparedStatement.setBigDecimal(7, bigDecimal);
                    preparedStatement.setString(8, "9999" + sum);
                    preparedStatement.setInt(9, 0);
                    preparedStatement.setDate(10, now);
                    preparedStatement.setDate(11, now);
                    preparedStatement.setDate(12, now);
                    preparedStatement.setInt(13, 0);
                    preparedStatement.addBatch();
                }
                int[] resultCount = preparedStatement.executeBatch();
                System.out.println("插入成功条数: " + resultCount.length);
            }
            connection.commit();
            long endTime = System.currentTimeMillis();
            System.out.println("插入时间: " + (endTime - startTime));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, null, preparedStatement);
        }

    }

    public static void main(String[] args) {
        OrderJDBCDao orderJDBCDao = new OrderJDBCDao();
        orderJDBCDao.insertBatchOrder();
    }

}
