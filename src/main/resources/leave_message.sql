/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : leave_message

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 30/12/2019 22:43:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员登陆所需ID',
  `a_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登陆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '123', '123');
INSERT INTO `admin` VALUES (30, '111', '111');
INSERT INTO `admin` VALUES (31, '2222', '111');

-- ----------------------------
-- Table structure for eg_contrast
-- ----------------------------
DROP TABLE IF EXISTS `eg_contrast`;
CREATE TABLE `eg_contrast`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `EXPID` int(11) NOT NULL COMMENT '对应的实验ID\n',
  `ID_Query` int(11) NULL DEFAULT NULL COMMENT '对应的查询表ID\n',
  `sensor03` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor04` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor05` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor06` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor07` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor08` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor09` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor10` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor11` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor12` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor13` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor02` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor15` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor16` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor17` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor18` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor19` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor20` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor21` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor22` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor23` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor24` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor25` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor26` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor27` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor28` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor29` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor30` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor31` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor32` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor14` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor01` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '肌电数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for electromyography
-- ----------------------------
DROP TABLE IF EXISTS `electromyography`;
CREATE TABLE `electromyography`  (
  `EXPID` int(11) NOT NULL COMMENT '实验ID\n',
  `ID_Query` int(11) NULL DEFAULT NULL COMMENT '对应实验ID\n',
  `TimeStamp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor01` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor02` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor03` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor04` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor05` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `sensor06` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor07` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor08` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor09` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor10` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor11` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor12` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor13` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor14` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor15` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor16` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor17` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor18` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor19` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor20` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor21` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor22` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor23` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor24` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor25` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor26` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor27` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor28` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor29` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor30` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor31` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor32` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EXPID`, `ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '肌电数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for experiments
-- ----------------------------
DROP TABLE IF EXISTS `experiments`;
CREATE TABLE `experiments`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实验时间',
  `ID_preec` int(11) NULL DEFAULT NULL,
  `EXPID_eg` int(11) NULL DEFAULT NULL,
  `EXPID_id` int(11) NULL DEFAULT NULL,
  `EXPID_ik` int(11) NULL DEFAULT NULL,
  `EXPID_kd` int(11) NULL DEFAULT NULL,
  `EXPID_ox` int(11) NULL DEFAULT NULL,
  `EXPID_eg_contrast` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实验表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inverse_dynamics
-- ----------------------------
DROP TABLE IF EXISTS `inverse_dynamics`;
CREATE TABLE `inverse_dynamics`  (
  `EXPID` int(11) NOT NULL COMMENT '实验ID\n',
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `ID_Query` int(11) NOT NULL,
  `TimeStamp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EXPID`, `ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '逆动力学数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inverse_kinematics
-- ----------------------------
DROP TABLE IF EXISTS `inverse_kinematics`;
CREATE TABLE `inverse_kinematics`  (
  `EXPID` int(11) NOT NULL COMMENT '实验ID\n',
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `ID_Query` int(11) NOT NULL,
  `TimeStamp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EXPID`, `ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '逆运动学学数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for kand
-- ----------------------------
DROP TABLE IF EXISTS `kand`;
CREATE TABLE `kand`  (
  `EXPID` int(11) NOT NULL COMMENT '实验ID\n',
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `ID_Query` int(11) NOT NULL,
  `TimeStamp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EXPID`, `ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '运动学与动力学数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oxygen
-- ----------------------------
DROP TABLE IF EXISTS `oxygen`;
CREATE TABLE `oxygen`  (
  `EXPID` int(11) NOT NULL COMMENT '实验ID\n',
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '单次实验数据标号',
  `ID_Query` int(11) NOT NULL,
  `TimeStamp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EXPID`, `ID`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '耗氧量数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for preec
-- ----------------------------
DROP TABLE IF EXISTS `preec`;
CREATE TABLE `preec`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Query` int(11) NOT NULL,
  `ID_Subjects` int(11) NULL DEFAULT NULL,
  `Advance` json NULL,
  `remark` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '前置实验条件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for subjects
-- ----------------------------
DROP TABLE IF EXISTS `subjects`;
CREATE TABLE `subjects`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TestDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试日期',
  `weight` int(11) NULL DEFAULT NULL COMMENT '公斤',
  `height` int(11) NULL DEFAULT NULL COMMENT '身高厘米',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '受试者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for usertest
-- ----------------------------
DROP TABLE IF EXISTS `usertest`;
CREATE TABLE `usertest`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
