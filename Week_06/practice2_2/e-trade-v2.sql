/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : e-trade

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 25/11/2020
*/

-- ----------------------------
-- v1 -> v2
-- 改进1  满足第三范式：消除依赖传递的列，比如：登录名<-用户级别
-- 改进2  冷热数据分离,减小表宽度
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
CREATE TABLE customer_login
(
    id   INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '用户ID',
    login_name    VARCHAR(20)                 NOT NULL COMMENT '用户登录名',
    password      CHAR(32)                    NOT NULL COMMENT 'md5加密的密码',
    user_stats    TINYINT                     NOT NULL DEFAULT 1 COMMENT '用户状态',
    modified_time TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_customerid (id)
) ENGINE = innodb COMMENT '用户登录表';

CREATE TABLE customer_inf
(
    id    INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    customer_id        INT UNSIGNED                NOT NULL COMMENT 'customer_login表的自增ID',
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

CREATE TABLE customer_level_inf
(
    id TINYINT      NOT NULL AUTO_INCREMENT COMMENT '会员级别ID',
    level_name     VARCHAR(10)  NOT NULL COMMENT '会员级别名称',
    min_point      INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '该级别最低积分',
    max_point      INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '该级别最高积分',
    modified_time  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_levelid (id)
) ENGINE = innodb COMMENT '用户级别信息表';

CREATE TABLE customer_addr
(
    id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '自增主键ID',
    customer_id      INT UNSIGNED                NOT NULL COMMENT 'customer_login表的自增ID',
    zip              SMALLINT                    NOT NULL COMMENT '邮编',
    province         SMALLINT                    NOT NULL COMMENT '地区表中省份的ID',
    city             SMALLINT                    NOT NULL COMMENT '地区表中城市的ID',
    district         SMALLINT                    NOT NULL COMMENT '地区表中的区ID',
    address          VARCHAR(200)                NOT NULL COMMENT '具体的地址门牌号',
    is_default       TINYINT                     NOT NULL COMMENT '是否默认',
    modified_time    TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_customeraddid (id)
) ENGINE = innodb COMMENT '用户地址表';

CREATE TABLE customer_point_log
(
    id     INT UNSIGNED     NOT NULL AUTO_INCREMENT COMMENT '积分日志ID',
    customer_id  INT UNSIGNED     NOT NULL COMMENT '用户ID',
    source       TINYINT UNSIGNED NOT NULL COMMENT '积分来源：0订单，1登陆，2活动',
    refer_number INT UNSIGNED     NOT NULL DEFAULT 0 COMMENT '积分来源相关编号',
    change_point SMALLINT         NOT NULL DEFAULT 0 COMMENT '变更积分数',
    create_time  TIMESTAMP        NOT NULL COMMENT '积分日志生成时间',
    PRIMARY KEY pk_pointid (id)
) ENGINE = innodb COMMENT '用户积分日志表';

CREATE TABLE customer_balance_log
(
    id  INT UNSIGNED     NOT NULL AUTO_INCREMENT COMMENT '余额日志ID',
    customer_id INT UNSIGNED     NOT NULL COMMENT '用户ID',
    source      TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '记录来源：1订单，2退货单',
    source_sn   INT UNSIGNED     NOT NULL COMMENT '相关单据ID',
    create_time TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录生成时间',
    amount      DECIMAL(8, 2)    NOT NULL DEFAULT 0.00 COMMENT '变动金额',
    PRIMARY KEY pk_balanceid (id)
) ENGINE = innodb COMMENT '用户余额变动表';

-- ----------------------------
-- Table structure for product
-- ----------------------------
CREATE TABLE brand_info
(
    id      SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '品牌ID',
    brand_name    VARCHAR(50)                      NOT NULL COMMENT '品牌名称',
    telephone     VARCHAR(50)                      NOT NULL COMMENT '联系电话',
    brand_web     VARCHAR(100) COMMENT '品牌网络',
    brand_logo    VARCHAR(100) COMMENT '品牌logo URL',
    brand_desc    VARCHAR(150) COMMENT '品牌描述',
    brand_status  TINYINT                          NOT NULL DEFAULT 0 COMMENT '品牌状态,0禁用,1启用',
    brand_order   TINYINT                          NOT NULL DEFAULT 0 COMMENT '排序',
    modified_time TIMESTAMP                        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_brandid (id)
) ENGINE = innodb COMMENT '品牌信息表';

CREATE TABLE product_category
(
    id     SMALLINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '分类ID',
    category_name   VARCHAR(10)                      NOT NULL COMMENT '分类名称',
    category_code   VARCHAR(10)                      NOT NULL COMMENT '分类编码',
    parent_id       SMALLINT UNSIGNED                NOT NULL DEFAULT 0 COMMENT '父分类ID',
    category_level  TINYINT                          NOT NULL DEFAULT 1 COMMENT '分类层级',
    category_status TINYINT                          NOT NULL DEFAULT 1 COMMENT '分类状态',
    modified_time   TIMESTAMP                        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_categoryid (id)
) ENGINE = innodb COMMENT '商品分类表';

CREATE TABLE supplier_info
(
    id     INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '供应商ID',
    supplier_code   CHAR(8)                     NOT NULL COMMENT '供应商编码',
    supplier_name   CHAR(50)                    NOT NULL COMMENT '供应商名称',
    supplier_type   TINYINT                     NOT NULL COMMENT '供应商类型：1.自营，2.平台',
    link_man        VARCHAR(10)                 NOT NULL COMMENT '供应商联系人',
    phone_number    VARCHAR(50)                 NOT NULL COMMENT '联系电话',
    bank_name       VARCHAR(50)                 NOT NULL COMMENT '供应商开户银行名称',
    bank_account    VARCHAR(50)                 NOT NULL COMMENT '银行账号',
    address         VARCHAR(200)                NOT NULL COMMENT '供应商地址',
    supplier_status TINYINT                     NOT NULL DEFAULT 0 COMMENT '状态：0禁止，1启用',
    modified_time   TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_supplierid (id)
) ENGINE = innodb COMMENT '供应商信息表';

CREATE TABLE product_info
(
    id        INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '商品ID',
    product_core      CHAR(16)                    NOT NULL COMMENT '商品编码',
    product_name      VARCHAR(20)                 NOT NULL COMMENT '商品名称',
    bar_code          VARCHAR(50)                 NOT NULL COMMENT '国条码',
    brand_id          INT UNSIGNED                NOT NULL COMMENT '品牌表的ID',
    one_category_id   SMALLINT UNSIGNED           NOT NULL COMMENT '一级分类ID',
    two_category_id   SMALLINT UNSIGNED           NOT NULL COMMENT '二级分类ID',
    three_category_id SMALLINT UNSIGNED           NOT NULL COMMENT '三级分类ID',
    supplier_id       INT UNSIGNED                NOT NULL COMMENT '商品的供应商ID',
    price             DECIMAL(8, 2)               NOT NULL COMMENT '商品销售价格',
    average_cost      DECIMAL(18, 2)              NOT NULL COMMENT '商品加权平均成本',
    publish_status    TINYINT                     NOT NULL DEFAULT 0 COMMENT '上下架状态：0下架1上架',
    audit_status      TINYINT                     NOT NULL DEFAULT 0 COMMENT '审核状态：0未审核，1已审核',
    weight            FLOAT COMMENT '商品重量',
    length            FLOAT COMMENT '商品长度',
    height            FLOAT COMMENT '商品高度',
    width             FLOAT COMMENT '商品宽度',
    color_type        ENUM ('红','黄','蓝','黑'),
    production_date   DATETIME                    NOT NULL COMMENT '生产日期',
    shelf_life        INT                         NOT NULL COMMENT '商品有效期',
    descript          TEXT                        NOT NULL COMMENT '商品描述',
    indate            TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品录入时间',
    modified_time     TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_productid (id)
) ENGINE = innodb COMMENT '商品信息表';

CREATE TABLE product_pic_info
(
    id INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '商品图片ID',
    product_id     INT UNSIGNED                NOT NULL COMMENT '商品ID',
    pic_desc       VARCHAR(50) COMMENT '图片描述',
    pic_url        VARCHAR(200)                NOT NULL COMMENT '图片URL',
    is_master      TINYINT                     NOT NULL DEFAULT 0 COMMENT '是否主图：0.非主图1.主图',
    pic_order      TINYINT                     NOT NULL DEFAULT 0 COMMENT '图片排序',
    pic_status     TINYINT                     NOT NULL DEFAULT 1 COMMENT '图片是否有效：0无效 1有效',
    modified_time  TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_picid (id)
) ENGINE = innodb COMMENT '商品图片信息表';

CREATE TABLE product_comment
(
    id    INT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '评论ID',
    product_id    INT UNSIGNED                NOT NULL COMMENT '商品ID',
    order_id      BIGINT UNSIGNED             NOT NULL COMMENT '订单ID',
    customer_id   INT UNSIGNED                NOT NULL COMMENT '用户ID',
    title         VARCHAR(50)                 NOT NULL COMMENT '评论标题',
    content       VARCHAR(300)                NOT NULL COMMENT '评论内容',
    audit_status  TINYINT                     NOT NULL COMMENT '审核状态：0未审核，1已审核',
    audit_time    TIMESTAMP                   NOT NULL COMMENT '评论时间',
    modified_time TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_commentid (id)
) ENGINE = innodb COMMENT '商品评论表';


-- ----------------------------
-- Table structure for order
-- ----------------------------
CREATE TABLE order_master
(
    id           INT UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    order_sn           BIGINT UNSIGNED NOT NULL COMMENT '订单编号 yyyymmddnnnnnnnn',
    customer_id        INT UNSIGNED    NOT NULL COMMENT '下单人ID',
    shipping_user      VARCHAR(10)     NOT NULL COMMENT '收货人姓名',
    province           SMALLINT        NOT NULL COMMENT '省',
    city               SMALLINT        NOT NULL COMMENT '市',
    district           SMALLINT        NOT NULL COMMENT '区',
    address            VARCHAR(100)    NOT NULL COMMENT '地址',
    payment_method     TINYINT         NOT NULL COMMENT '支付方式：1现金，2余额，3网银，4支付宝，5微信',
    order_money        DECIMAL(8, 2)   NOT NULL COMMENT '订单金额',
    district_money     DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
    shipping_money     DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '运费金额',
    payment_money      DECIMAL(8, 2)   NOT NULL DEFAULT 0.00 COMMENT '支付金额',
    shipping_comp_name VARCHAR(10) COMMENT '快递公司名称',
    shipping_sn        VARCHAR(50) COMMENT '快递单号',
    create_time        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    shipping_time      DATETIME COMMENT '发货时间',
    pay_time           DATETIME COMMENT '支付时间',
    receive_time       DATETIME COMMENT '收货时间',
    order_status       TINYINT         NOT NULL DEFAULT 0 COMMENT '订单状态',
    order_point        INT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '订单积分',
    invoice_time       VARCHAR(100) COMMENT '发票抬头',
    modified_time      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_orderid (id)
) ENGINE = innodb COMMENT '订单主表';

CREATE TABLE order_detail
(
    id INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '订单详情表ID',
    order_id        INT UNSIGNED  NOT NULL COMMENT '订单表ID',
    product_id      INT UNSIGNED  NOT NULL COMMENT '订单商品ID',
    product_name    VARCHAR(50)   NOT NULL COMMENT '商品名称',
    product_cnt     INT           NOT NULL DEFAULT 1 COMMENT '购买商品数量',
    product_price   DECIMAL(8, 2) NOT NULL COMMENT '购买商品单价',
    average_cost    DECIMAL(8, 2) NOT NULL COMMENT '平均成本价格',
    weight          FLOAT COMMENT '商品重量',
    fee_money       DECIMAL(8, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠分摊金额',
    w_id            INT UNSIGNED  NOT NULL COMMENT '仓库ID',
    modified_time   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_orderdetailid (id)
) ENGINE = innodb COMMENT '订单详情表';

CREATE TABLE order_cart
(
    id        INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    customer_id    INT UNSIGNED  NOT NULL COMMENT '用户ID',
    product_id     INT UNSIGNED  NOT NULL COMMENT '商品ID',
    product_amount INT           NOT NULL COMMENT '加入购物车商品数量',
    price          DECIMAL(8, 2) NOT NULL COMMENT '商品价格',
    add_time       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
    modified_time  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_cartid (id)
) ENGINE = innodb COMMENT '购物车表';

CREATE TABLE warehouse_info
(
    id             SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
    warehouse_sn     CHAR(5)           NOT NULL COMMENT '仓库编码',
    warehoust_name   VARCHAR(10)       NOT NULL COMMENT '仓库名称',
    warehouse_phone  VARCHAR(20)       NOT NULL COMMENT '仓库电话',
    contact          VARCHAR(10)       NOT NULL COMMENT '仓库联系人',
    province         SMALLINT          NOT NULL COMMENT '省',
    city             SMALLINT          NOT NULL COMMENT '市',
    distrct          SMALLINT          NOT NULL COMMENT '区',
    address          VARCHAR(100)      NOT NULL COMMENT '仓库地址',
    warehouse_status TINYINT           NOT NULL DEFAULT 1 COMMENT '仓库状态：0禁用，1启用',
    modified_time    TIMESTAMP         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_wid (id)
) ENGINE = innodb COMMENT '仓库信息表';

CREATE TABLE warehouse_product
(
    id          INT UNSIGNED      NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
    product_id     INT UNSIGNED      NOT NULL COMMENT '商品ID',
    w_id           SMALLINT UNSIGNED NOT NULL COMMENT '仓库ID',
    current_cnt    INT UNSIGNED      NOT NULL DEFAULT 0 COMMENT '当前商品数量',
    lock_cnt       INT UNSIGNED      NOT NULL DEFAULT 0 COMMENT '当前占用数据',
    in_transit_cnt INT UNSIGNED      NOT NULL DEFAULT 0 COMMENT '在途数据',
    average_cost   DECIMAL(8, 2)     NOT NULL DEFAULT 0.00 COMMENT '移动加权成本',
    modified_time  TIMESTAMP         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_wpid (id)
) ENGINE = innodb COMMENT '商品库存表';

CREATE TABLE shipping_info
(
    id       TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    ship_name     VARCHAR(20)      NOT NULL COMMENT '物流公司名称',
    ship_contact  VARCHAR(20)      NOT NULL COMMENT '物流公司联系人',
    telephone     VARCHAR(20)      NOT NULL COMMENT '物流公司联系电话',
    price         DECIMAL(8, 2)    NOT NULL DEFAULT 0.00 COMMENT '配送价格',
    modified_time TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY pk_shipid (id)
) ENGINE = innodb COMMENT '物流公司信息表';
