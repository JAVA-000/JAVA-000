package com.gjz.test.ss.jdbc.cocept;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <pre>
 * 创建表
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/9
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@SpringBootTest
public class CreateTableTest {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void testDropTable(){
        String sql = "drop table if exists t_order";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void testCreateOrderTable(){
        String sql = "-- test.t_order definition\n" +
                "\n" +
                "CREATE TABLE `t_order` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "  `user_id` bigint(19) NOT NULL COMMENT '用户ID',\n" +
                "  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',\n" +
                "  `user_name` varchar(64) DEFAULT NULL COMMENT '用户帐号',\n" +
                "  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',\n" +
                "  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '应付金额（实际支付金额）',\n" +
                "  `freight_amount` decimal(10,2) DEFAULT NULL COMMENT '运费金额',\n" +
                "  `pay_type` int(10) DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',\n" +
                "  `status` int(10) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',\n" +
                "  `address_id` bigint(20) DEFAULT NULL COMMENT '配送地址ID',\n" +
                "  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司(配送方式)',\n" +
                "  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流单号',\n" +
                "  `remark` varchar(500) DEFAULT NULL COMMENT '订单备注',\n" +
                "  `confirm_status` int(10) DEFAULT NULL COMMENT '确认收货状态：0->未确认；1->已确认',\n" +
                "  `delete_status` int(10) NOT NULL COMMENT '删除状态：0->未删除；1->已删除',\n" +
                "  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',\n" +
                "  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',\n" +
                "  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',\n" +
                "  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',\n" +
                "  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `last_updated_by` varchar(32) DEFAULT NULL COMMENT '最后修改人',\n" +
                "  `last_updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';";

        jdbcTemplate.execute(sql);
    }

}

