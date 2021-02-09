CREATE DATABASE etrade;
USE etrade;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_customer
-- ----------------------------
CREATE TABLE tb_customer
(
    id                 INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    customer_name      VARCHAR(20)                 NOT NULL COMMENT '用户真实姓名',
    identity_card_type TINYINT                     NOT NULL DEFAULT 1 COMMENT '证件类型：1 身份证，2 军官证，3 护照',
    identity_card_no   VARCHAR(20) COMMENT '证件号码',
    mobile_phone       INT UNSIGNED COMMENT '手机号',
    customer_email     VARCHAR(50) COMMENT '邮箱',
    gender             CHAR(1) COMMENT '性别',
    user_point         INT                         NOT NULL DEFAULT 0 COMMENT '用户积分',
    register_time      TIMESTAMP                   NOT NULL COMMENT '注册时间',
    birthday           DATETIME COMMENT '会员生日',
    customer_level     TINYINT                     NOT NULL DEFAULT 1 COMMENT '会员级别：1 普通会员，2 青铜，3白银，4黄金，5钻石',
    user_money         DECIMAL(8, 2)               NOT NULL DEFAULT 0.00 COMMENT '用户余额',
    modified_time      TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_customerinfid (id)
) ENGINE = innodb COMMENT '用户信息表';

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
CREATE TABLE tb_product
(
    id              INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '商品ID',
    product_core    CHAR(16)                    NOT NULL COMMENT '商品编码',
    product_name    VARCHAR(20)                 NOT NULL COMMENT '商品名称',
    bar_code        VARCHAR(50)                 NOT NULL COMMENT '国条码',
    brand_id        INT UNSIGNED                NOT NULL COMMENT '品牌表的ID',
    category_id     SMALLINT UNSIGNED           NOT NULL COMMENT '分类ID',
    supplier_id     INT UNSIGNED                NOT NULL COMMENT '商品的供应商ID',
    price           DECIMAL(8, 2)               NOT NULL COMMENT '商品销售价格',
    average_cost    DECIMAL(18, 2)              NOT NULL COMMENT '商品加权平均成本',
    publish_status  TINYINT                     NOT NULL DEFAULT 0 COMMENT '上下架状态：0下架1上架',
    audit_status    TINYINT                     NOT NULL DEFAULT 0 COMMENT '审核状态：0未审核，1已审核',
    weight          FLOAT COMMENT '商品重量',
    length          FLOAT COMMENT '商品长度',
    height          FLOAT COMMENT '商品高度',
    width           FLOAT COMMENT '商品宽度',
    color_type      ENUM ('红','黄','蓝','黑'),
    production_date DATETIME                    NOT NULL COMMENT '生产日期',
    shelf_life      INT                         NOT NULL COMMENT '商品有效期',
    descript        TEXT                        NOT NULL COMMENT '商品描述',
    indate          TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品录入时间',
    modified_time   TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_productid (id)
) ENGINE = innodb COMMENT '商品信息表';

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
CREATE TABLE tb_order
(
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    order_sn      VARCHAR(100) NOT NULL COMMENT '订单编号 yyyymmddnnnnnnnn',
    customer_id   INT UNSIGNED NOT NULL COMMENT '下单人ID',
    product_id    INT UNSIGNED NOT NULL COMMENT '订单商品ID',
#     shipping_user      VARCHAR(10)     NOT NULL COMMENT '收货人姓名',
#     province           SMALLINT        NOT NULL COMMENT '省',
#     city               SMALLINT        NOT NULL COMMENT '市',
#     district           SMALLINT        NOT NULL COMMENT '区',
#     address            VARCHAR(100)    NOT NULL COMMENT '地址',
#     payment_method     TINYINT         NOT NULL COMMENT '支付方式：1现金，2余额，3网银，4支付宝，5微信',
#     order_money        DECIMAL(8, 2)   NOT NULL COMMENT '订单金额',
#     district_money     DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
#     shipping_money     DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '运费金额',
#     payment_money      DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '支付金额',
#     shipping_comp_name VARCHAR(10) COMMENT '快递公司名称',
#     shipping_sn        VARCHAR(50) COMMENT '快递单号',
    create_time   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
#     shipping_time      DATETIME COMMENT '发货时间',
#     pay_time           DATETIME COMMENT '支付时间',
#     receive_time       DATETIME COMMENT '收货时间',
#     order_status       TINYINT         NOT NULL DEFAULT 0 COMMENT '订单状态',
#     order_point        INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '订单积分',
#     invoice_time       VARCHAR(100) COMMENT '发票抬头',
    modified_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_orderid (id)
) ENGINE = innodb COMMENT '订单主表';