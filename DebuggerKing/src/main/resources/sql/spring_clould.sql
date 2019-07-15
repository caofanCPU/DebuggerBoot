/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL-3306
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : spring_clould

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 16/07/2019 01:36:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_dict`;
CREATE TABLE `sys_config_dict` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) unsigned NOT NULL DEFAULT '0' COMMENT ' 父ID',
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `sort_id` tinyint(4) unsigned DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_config_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_config_dict` VALUES (1, 0, 'AONY', '德玛', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
