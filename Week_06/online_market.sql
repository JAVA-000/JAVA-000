CREATE TABLE `zlh_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(32) NOT NULL COMMENT '名称，做登录名',
  `nike_name` VARCHAR(32) NULL COMMENT '昵称',
  `password` VARCHAR(32) NULL COMMENT '密码',
  `tel` VARCHAR(13) NULL COMMENT '手机号',
  `email` VARCHAR(16) NULL COMMENT '邮箱',
  `head_pic` VARCHAR(32) NULL COMMENT '头像地址',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '用户表';


CREATE TABLE `zlh_product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `product_code` VARCHAR(32) NOT NULL COMMENT '商品编码',
  `product_name` VARCHAR(32) NOT NULL COMMENT '商品名称',
  `product_type` TINYINT(2) NULL COMMENT '商品类型',
  `product_detail` VARCHAR(256) NULL COMMENT '商品详情',
  `product_pic` VARCHAR(32) NULL COMMENT '商品图片',
  `product_price` DECIMAL(12,2) NOT NULL COMMENT '商品价格',
  `product_discount` DECIMAL(5,2) NULL COMMENT '商品折扣',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '商品表';

CREATE TABLE `zlh_product_snapshot` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `product_code` VARCHAR(32) NOT NULL COMMENT '商品编码',
  `product_name` VARCHAR(32) NOT NULL COMMENT '商品名称',
  `product_type` TINYINT(2) NULL COMMENT '商品类型',
  `product_detail` VARCHAR(256) NULL COMMENT '商品详情',
  `product_pic` VARCHAR(32) NULL COMMENT '商品图片',
  `product_price` DECIMAL(12,2) NOT NULL COMMENT '商品价格',
  `product_discount` DECIMAL(5,2) NULL COMMENT '商品折扣',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '商品快照表';

CREATE TABLE `zlh_product_review` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT(20) NOT NULL COMMENT '商品id',
  `user_id` BIGINT(20) NOT NULL COMMENT '评价用户id',
  `nike_name` VARCHAR(32) NULL COMMENT '评价用户昵称',
  `review_start_level` TINYINT(2) NOT NULL DEFAULT 5 COMMENT '评价星级 1 表示1星，2表示2星，3表示3星， 4表示4星，5表示5星',
  `product_review_detail` VARCHAR(256) NULL COMMENT '商品评价详情',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '商品评价';

CREATE TABLE `zlh_user_address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `is_default` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址：0 否，1 是',
  `nation_code` VARCHAR(8) NULL COMMENT '国家名称',
  `nation_name` VARCHAR(32) NULL COMMENT '国家名称',
  `province_code` VARCHAR(8) NULL COMMENT '省份编码',
  `province_name` VARCHAR(32) NULL COMMENT '省份名称',
  `city_code` VARCHAR(8) NULL COMMENT '地市编码',
  `city_name` VARCHAR(32) NULL COMMENT '城市名称',
  `district_code` VARCHAR(8) NULL COMMENT '区县编码',
  `district_name` VARCHAR(32) NULL COMMENT '区县名称',
  `address` VARCHAR(256) NULL COMMENT '详细地址',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '用户收件地址表';

CREATE TABLE `test1`.`zlh_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `product_snapshot_id` BIGINT(20) NOT NULL COMMENT '商品快照ID',
  `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
  `total_fee` DECIMAL(12,2) NULL DEFAULT 0 COMMENT '商品总价',
  `express_fee` DECIMAL(6,2) NULL DEFAULT 0 COMMENT '快递费',
  `discount_fee` DECIMAL(12,2) NULL COMMENT '折扣费用',
  `real_payment_fee` DECIMAL(12,2) NULL COMMENT '实付费用',
  `pay_flow_no` VARCHAR(32) NULL COMMENT '付款流水号',
  `order_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '订单状态 0 创建，1 支付中 2 支付成功 3 关闭订单 4 支付失败',
  `order_create_time` DATETIME NOT NULL COMMENT '订单创建时间',
  `payment_time` DATETIME NULL COMMENT '付款时间',
  `express_time` DATETIME NULL COMMENT '发货时间',
  `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0 正常 1删除',
  PRIMARY KEY (`id`))
COMMENT = '订单主表';



