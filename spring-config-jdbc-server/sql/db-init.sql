/*
 Navicat Premium Data Transfer

 Source Server         : mysql@localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : alex

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 28/09/2018 16:44:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `id` int(32) NOT NULL,
  `application` varchar(64) DEFAULT NULL,
  `profile` varchar(64) DEFAULT NULL,
  `label` varchar(64) DEFAULT NULL,
  `key` varchar(64) DEFAULT NULL,
  `value` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of properties
-- ----------------------------
BEGIN;
INSERT INTO `properties` VALUES (1, 'config-client', 'test-profile', 'master', 'test', 'Hello World');
INSERT INTO `properties` VALUES (2, 'config-client', 'test-profile', 'master', 'test2', '测试');
INSERT INTO `properties` VALUES (3, 'config-client1', 'test-profile', 'master', 'test', '测试打发的说法');
INSERT INTO `properties` VALUES (4, 'config-client', 'test-profile', 'master1', 'test2', '测试');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
