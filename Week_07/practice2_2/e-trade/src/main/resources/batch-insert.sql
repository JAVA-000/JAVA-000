-- # 插入数据到订单表中，关闭自动提交，批量插入
DROP PROCEDURE IF EXISTS tb_order_init;
DELIMITER $
CREATE PROCEDURE tb_order_init()
BEGIN
    DECLARE i INT DEFAULT 1;
    set autocommit = 0;
    WHILE i <= 1000000
        DO
            insert into etrade.tb_order (order_sn, customer_id, product_id)
            VALUES (CONCAT(unix_timestamp(), '_', CEILING(rand() * 100)), CEILING(rand() * 100), CEILING(rand() * 100));
            SET i = i + 1;
        END WHILE;
    commit;
END $
CALL tb_order_init();

-- # 插入数据到订单表中，自动提交，一条一条的插入
DROP PROCEDURE IF EXISTS orders_initData_one;
DELIMITER $
CREATE PROCEDURE orders_initData_one()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 1000000
        DO
            insert into etrade.tb_order (order_sn, customer_id, product_id)
            VALUES (CONCAT(unix_timestamp(), '_', CEILING(rand() * 100)), CEILING(rand() * 100), CEILING(rand() * 100));
            SET i = i + 1;
        END WHILE;
END $
CALL orders_initData_one();

select count(*) from tb_order;