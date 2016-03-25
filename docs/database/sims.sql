/*
Navicat MySQL Data Transfer

Source Server         : localhost_conn
Source Server Version : 50173
Source Host           : localhost
Source Database       : sims

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-03-25 08:54:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `credit` tinyint(4) NOT NULL DEFAULT '0',
  `material` varchar(256) DEFAULT NULL,
  `ec_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(128) NOT NULL,
  `type` varchar(60) DEFAULT NULL,
  `ec_id` int(11) NOT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_action
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_action`;
CREATE TABLE `t_jsrush_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(255) DEFAULT NULL,
  `action_text` varchar(255) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `target_url` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCE07D309332546DE` (`pid`),
  CONSTRAINT `FKCE07D309332546DE` FOREIGN KEY (`pid`) REFERENCES `t_jsrush_action` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_bizaction_log
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_bizaction_log`;
CREATE TABLE `t_jsrush_bizaction_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(255) DEFAULT NULL,
  `biz_content` varchar(255) DEFAULT NULL,
  `biz_info` longtext,
  `biz_type` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `creator_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_bizaction_log_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_bizaction_log_permissions`;
CREATE TABLE `t_jsrush_bizaction_log_permissions` (
  `log_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`log_id`,`role_id`),
  KEY `FK8402D120CBA05057` (`role_id`),
  KEY `FK8402D120727F85BF` (`log_id`),
  CONSTRAINT `FK8402D120727F85BF` FOREIGN KEY (`log_id`) REFERENCES `t_jsrush_bizaction_log` (`id`),
  CONSTRAINT `FK8402D120CBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_ec
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_ec`;
CREATE TABLE `t_jsrush_ec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_state` int(11) DEFAULT NULL,
  `corp_account` varchar(255) DEFAULT NULL,
  `corp_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `link_man` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_ec_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_ec_permissions`;
CREATE TABLE `t_jsrush_ec_permissions` (
  `ec_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ec_id`,`role_id`),
  KEY `FKEB573436D1EBF297` (`ec_id`),
  KEY `FKEB573436CBA05057` (`role_id`),
  CONSTRAINT `FKEB573436CBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`),
  CONSTRAINT `FKEB573436D1EBF297` FOREIGN KEY (`ec_id`) REFERENCES `t_jsrush_ec` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_role
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_role`;
CREATE TABLE `t_jsrush_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `ec_id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `open_register` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK4CB2EE09D1EBF297` (`ec_id`),
  KEY `FK4CB2EE0979906C5E` (`pid`),
  CONSTRAINT `FK4CB2EE0979906C5E` FOREIGN KEY (`pid`) REFERENCES `t_jsrush_role` (`id`),
  CONSTRAINT `FK4CB2EE09D1EBF297` FOREIGN KEY (`ec_id`) REFERENCES `t_jsrush_ec` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_role_action
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_role_action`;
CREATE TABLE `t_jsrush_role_action` (
  `role_id` bigint(20) NOT NULL,
  `action_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`action_id`),
  KEY `FKB24F612C3EF55517` (`action_id`),
  KEY `FKB24F612CCBA05057` (`role_id`),
  CONSTRAINT `FKB24F612CCBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`),
  CONSTRAINT `FKB24F612C3EF55517` FOREIGN KEY (`action_id`) REFERENCES `t_jsrush_action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_role_permissions`;
CREATE TABLE `t_jsrush_role_permissions` (
  `role_id` bigint(20) NOT NULL,
  `prole_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`prole_id`),
  KEY `FK22D87B4ECBA05057` (`role_id`),
  KEY `FK22D87B4E3E23F1E7` (`prole_id`),
  CONSTRAINT `FK22D87B4E3E23F1E7` FOREIGN KEY (`prole_id`) REFERENCES `t_jsrush_role` (`id`),
  CONSTRAINT `FK22D87B4ECBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_user
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_user`;
CREATE TABLE `t_jsrush_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area_information` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4CB4595ECBA05057` (`role_id`),
  CONSTRAINT `FK4CB4595ECBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jsrush_user_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_jsrush_user_permissions`;
CREATE TABLE `t_jsrush_user_permissions` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK1E928F23CBA05057` (`role_id`),
  KEY `FK1E928F2370CB1437` (`user_id`),
  CONSTRAINT `FK1E928F2370CB1437` FOREIGN KEY (`user_id`) REFERENCES `t_jsrush_user` (`id`),
  CONSTRAINT `FK1E928F23CBA05057` FOREIGN KEY (`role_id`) REFERENCES `t_jsrush_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_speciality
-- ----------------------------
DROP TABLE IF EXISTS `t_speciality`;
CREATE TABLE `t_speciality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `code` varchar(128) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `degree_type` varchar(128) DEFAULT NULL,
  `require_credit` tinyint(4) DEFAULT NULL,
  `option_credit` tinyint(4) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `ec_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='专业';

-- ----------------------------
-- Table structure for t_speciality_course
-- ----------------------------
DROP TABLE IF EXISTS `t_speciality_course`;
CREATE TABLE `t_speciality_course` (
  `course_id` int(11) NOT NULL,
  `speciality_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`,`speciality_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_no` varchar(64) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT '0',
  `birth_day` date DEFAULT NULL,
  `nation` varchar(128) DEFAULT NULL,
  `home_addr` varchar(256) DEFAULT NULL,
  `mobile` varchar(16) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `id_no` varchar(64) DEFAULT NULL,
  `political_status` tinyint(4) DEFAULT NULL,
  `enroll_date` date DEFAULT NULL,
  `graduation_date` date DEFAULT NULL,
  `degree_date` date DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `speciality_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `ec_id` int(11) DEFAULT NULL,
  `audit_state` tinyint(4) NOT NULL DEFAULT '0',
  `audit_time` datetime DEFAULT NULL,
  `audit_remark` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_student_course
-- ----------------------------
DROP TABLE IF EXISTS `t_student_course`;
CREATE TABLE `t_student_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` varchar(16) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `ec_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
