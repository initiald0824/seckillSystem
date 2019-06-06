/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-06-06 17:32:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存, -1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphoneX', 'Apple iPhoneX (A1865) 64GB 银色 移动联通电信4G手机', 'https://img14.360buyimg.com/n0/jfs/t11017/66/2838387439/262768/ffdaae1c/5cd942a1Nc67ea837.png', 'Apple iPhoneX (A1865) 64GB 银色 移动联通电信4G手机', '5400.00', '0');
INSERT INTO `goods` VALUES ('2', '华为Mate 9', '华为Mate 9', 'https://img14.360buyimg.com/n0/jfs/t7111/281/2528946315/109625/25a03126/59969f73Nddf3f773.jpg', '华为（HUAWEI） 华为 Mate9 全网通4G智能手机 黑色 全网通（4G+64G）', '1738.00', '0');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1pc, 2android, 3ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态, 0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', '1', '0.01', '4', '2019-06-06 17:08:00', '2019-06-09 11:00:00');
INSERT INTO `seckill_goods` VALUES ('2', '2', '0.01', '4', '2019-06-05 17:05:58', '2019-06-05 17:06:03');

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_user
-- ----------------------------
DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号码',
  `nickname` varchar(255) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt)',
  `salt` varchar(10) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of seckill_user
-- ----------------------------
INSERT INTO `seckill_user` VALUES ('110', 'test', '3d13ef76b11f9f20c27a83ed950281b4', 'initiald', null, '2019-05-22 09:37:06', '2019-05-22 09:37:08', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'initiald');
INSERT INTO `user` VALUES ('2', '2222');
