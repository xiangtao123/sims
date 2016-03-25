/*
Navicat MySQL Data Transfer

Source Server         : localhost_conn
Source Server Version : 50173
Source Host           : localhost
Source Database       : sims

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001
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
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('1', '计算机应用基础', '4', '计算机应用基础（人民邮电出版社09年出版）：978-7-115-19899-0', '1');
INSERT INTO `t_course` VALUES ('2', '高等数学', '4', '高等数学', '1');

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
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('2', '宇航学院', '', '1', '强地、扬信、拓天', null);
INSERT INTO `t_dept` VALUES ('3', '计算机学院', '', '1', '', null);
INSERT INTO `t_dept` VALUES ('4', '软件学院', '', '1', '', null);
INSERT INTO `t_dept` VALUES ('5', '机电学院', '', '1', '', null);
INSERT INTO `t_dept` VALUES ('6', '生命学院', '生物科技', '1', '', null);
INSERT INTO `t_dept` VALUES ('7', '现代远程教育学院', '', '1', '', null);

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
-- Records of t_jsrush_action
-- ----------------------------
INSERT INTO `t_jsrush_action` VALUES ('1', 'page:welcome', '登录', '0', '登录系统', null, 'gateway', null);
INSERT INTO `t_jsrush_action` VALUES ('2', 'module:system_manager', '系统管理', '0', '系统管理', null, 'module', '1');
INSERT INTO `t_jsrush_action` VALUES ('3', 'page:operate_log', '操作日志', '0', '查看操作日志报表', '/dispatcher/system/biz_operate_log', 'page', '2');
INSERT INTO `t_jsrush_action` VALUES ('4', 'page:role_action', '角色管理', '0', '角色管理', '/role/index', 'page', '2');
INSERT INTO `t_jsrush_action` VALUES ('5', 'page:user', '用户管理', '0', '用户管理', '/dispatcher/system/biz_user', 'page', '2');
INSERT INTO `t_jsrush_action` VALUES ('6', 'page:ec', '企业管理', '0', '企业管理', '/dispatcher/system/biz_ec', 'page', '2');
INSERT INTO `t_jsrush_action` VALUES ('7', 'element:action:cud', '系统资源维护', '0', '增删改', null, 'btn', '4');
INSERT INTO `t_jsrush_action` VALUES ('8', 'module:school_manager', '学校管理', '2', '', '', 'module', '1');
INSERT INTO `t_jsrush_action` VALUES ('9', 'page:dept', '院系管理', '0', '', '/dept/index', 'page', '8');
INSERT INTO `t_jsrush_action` VALUES ('10', 'page:speciality_manager', '专业管理', '0', '', '/speciality/index', 'page', '8');
INSERT INTO `t_jsrush_action` VALUES ('11', 'page:course_management', '课程管理', '0', '', '/course/index', 'page', '8');
INSERT INTO `t_jsrush_action` VALUES ('12', 'page:student_management', '学生管理', '0', '', '/student/index', 'page', '8');
INSERT INTO `t_jsrush_action` VALUES ('13', 'module:student_function', '学生功能', '3', '', '', 'module', '1');
INSERT INTO `t_jsrush_action` VALUES ('14', 'page:student_info', '个人信息', '0', '', '/student/view_info', 'page', '13');
INSERT INTO `t_jsrush_action` VALUES ('15', 'page:student_take_course', '在线选课', '1', '', '/student_take_course/index', 'page', '13');
INSERT INTO `t_jsrush_action` VALUES ('16', 'page:query_score', '成绩查询', '2', '', '/query_score/index', 'page', '13');
INSERT INTO `t_jsrush_action` VALUES ('17', 'page:take_course_manage', '选课成绩管理', '5', '', '/take_course/index', 'page', '8');

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
-- Records of t_jsrush_bizaction_log
-- ----------------------------

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
-- Records of t_jsrush_bizaction_log_permissions
-- ----------------------------
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('1', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('2', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('3', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('4', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('5', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('6', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('7', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('8', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('9', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('10', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('11', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('12', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('13', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('14', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('15', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('16', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('17', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('18', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('19', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('20', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('21', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('22', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('23', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('24', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('25', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('26', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('27', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('28', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('29', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('30', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('31', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('32', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('33', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('34', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('35', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('36', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('37', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('38', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('39', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('40', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('41', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('42', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('43', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('44', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('45', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('46', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('47', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('48', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('49', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('50', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('51', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('52', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('53', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('54', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('55', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('56', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('57', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('58', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('59', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('60', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('61', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('62', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('63', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('64', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('65', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('66', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('67', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('68', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('69', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('70', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('71', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('72', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('73', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('74', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('75', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('76', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('77', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('78', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('80', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('81', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('82', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('83', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('84', '2');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('15', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('19', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('21', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('29', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('31', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('32', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('33', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('34', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('35', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('36', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('37', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('38', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('39', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('41', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('42', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('43', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('45', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('46', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('48', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('49', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('50', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('51', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('52', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('53', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('54', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('56', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('57', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('58', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('59', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('60', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('61', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('62', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('63', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('64', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('66', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('68', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('70', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('71', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('72', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('73', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('74', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('75', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('76', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('77', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('80', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('82', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('83', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('84', '3');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('76', '5');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('77', '5');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('80', '5');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('84', '5');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('76', '7');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('77', '7');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('80', '7');
INSERT INTO `t_jsrush_bizaction_log_permissions` VALUES ('84', '7');

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
-- Records of t_jsrush_ec
-- ----------------------------
INSERT INTO `t_jsrush_ec` VALUES ('1', null, 'bit-001', '北京理工大学', null, '翟寿涛', '13200000000', null);

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
-- Records of t_jsrush_ec_permissions
-- ----------------------------

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
-- Records of t_jsrush_role
-- ----------------------------
INSERT INTO `t_jsrush_role` VALUES ('2', '超级系统管理员', null, null, '0');
INSERT INTO `t_jsrush_role` VALUES ('3', '北京理工大学企业管理员', '1', '2', '0');
INSERT INTO `t_jsrush_role` VALUES ('5', '软件学院', '1', '3', '0');
INSERT INTO `t_jsrush_role` VALUES ('6', '管理与经济学院', '1', '3', '0');
INSERT INTO `t_jsrush_role` VALUES ('7', '软件学院学生（2014年度）', '1', '5', '1');
INSERT INTO `t_jsrush_role` VALUES ('8', '经管学院学生（2014年度）', '1', '6', '1');

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
-- Records of t_jsrush_role_action
-- ----------------------------
INSERT INTO `t_jsrush_role_action` VALUES ('2', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '1');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '2');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '3');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '4');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '4');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '5');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '5');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '5');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '5');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '6');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '7');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '8');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '8');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '8');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '8');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '9');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '9');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '10');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '10');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '11');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '11');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '11');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '12');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '12');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '12');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '12');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '13');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '14');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '15');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('7', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('8', '16');
INSERT INTO `t_jsrush_role_action` VALUES ('2', '17');
INSERT INTO `t_jsrush_role_action` VALUES ('3', '17');
INSERT INTO `t_jsrush_role_action` VALUES ('5', '17');
INSERT INTO `t_jsrush_role_action` VALUES ('6', '17');

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
-- Records of t_jsrush_role_permissions
-- ----------------------------
INSERT INTO `t_jsrush_role_permissions` VALUES ('3', '2');
INSERT INTO `t_jsrush_role_permissions` VALUES ('5', '2');
INSERT INTO `t_jsrush_role_permissions` VALUES ('5', '3');
INSERT INTO `t_jsrush_role_permissions` VALUES ('5', '5');
INSERT INTO `t_jsrush_role_permissions` VALUES ('6', '2');
INSERT INTO `t_jsrush_role_permissions` VALUES ('6', '3');
INSERT INTO `t_jsrush_role_permissions` VALUES ('6', '6');
INSERT INTO `t_jsrush_role_permissions` VALUES ('7', '2');
INSERT INTO `t_jsrush_role_permissions` VALUES ('7', '3');
INSERT INTO `t_jsrush_role_permissions` VALUES ('7', '5');
INSERT INTO `t_jsrush_role_permissions` VALUES ('7', '7');
INSERT INTO `t_jsrush_role_permissions` VALUES ('8', '2');
INSERT INTO `t_jsrush_role_permissions` VALUES ('8', '3');
INSERT INTO `t_jsrush_role_permissions` VALUES ('8', '6');
INSERT INTO `t_jsrush_role_permissions` VALUES ('8', '8');

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
-- Records of t_jsrush_user
-- ----------------------------
INSERT INTO `t_jsrush_user` VALUES ('1', null, null, 'admin', '柳海龙', '901fcd35f7ce5bc18c1bde912a18a53403482e70', '2016-03-10 09:53:25', '090e638ee6807075', '2');
INSERT INTO `t_jsrush_user` VALUES ('2', null, 'bit_admin@bit.edu.com.cn', 'bit_admin', '翟寿涛', 'fb56f38fdafe8ece73198a4f5a06b9feb2b77539', '2016-03-11 22:56:17', '7ba23c0106785cb5', '3');
INSERT INTO `t_jsrush_user` VALUES ('3', null, '', '440901197709194316', '魏嘉志', 'd1dd65739ee89923caa35d33b832a25cef880339', '2016-03-24 16:31:10', '1c1a6427725109bd', '7');

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
-- Records of t_jsrush_user_permissions
-- ----------------------------
INSERT INTO `t_jsrush_user_permissions` VALUES ('2', '2');
INSERT INTO `t_jsrush_user_permissions` VALUES ('3', '2');
INSERT INTO `t_jsrush_user_permissions` VALUES ('3', '3');
INSERT INTO `t_jsrush_user_permissions` VALUES ('3', '5');
INSERT INTO `t_jsrush_user_permissions` VALUES ('3', '7');

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
-- Records of t_speciality
-- ----------------------------
INSERT INTO `t_speciality` VALUES ('1', '计算机科学与技术', '0010011001', '4', '工学', '57', '23', '3', '1');
INSERT INTO `t_speciality` VALUES ('2', '会计学', '20010012001', '4', '理学', '57', '23', '7', '1');

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
-- Records of t_speciality_course
-- ----------------------------
INSERT INTO `t_speciality_course` VALUES ('1', '1');
INSERT INTO `t_speciality_course` VALUES ('1', '2');
INSERT INTO `t_speciality_course` VALUES ('2', '1');

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
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('1', '101020160610001', '张可', '1', '1990-08-01', '汉族', '天津市和平区', '13600000012', 'zhangke@163.com', '371481119011086542', '1', '2014-09-01', '2016-03-01', '2016-03-01', '2016-03-22 21:07:49', '1', null, '1', '1', '2016-03-22 22:14:37', '通过');
INSERT INTO `t_student` VALUES ('2', '10000100001234', '卜雅艳', '2', '1987-06-26', '汉', '四川省达州市通川区', '13200000001', '', '51170219870626850X', '1', '2013-01-01', '2016-03-22', '2016-03-01', '2016-03-22 21:12:53', '1', null, '1', '2', '2016-03-22 22:12:51', '请填写个人邮箱');
INSERT INTO `t_student` VALUES ('3', '10000100001235', '岑文丽', '2', '1987-01-01', '汉', '四川省达州市通川区', '13200000025', 'cenwenli@163.com', '511702198707271621', '1', '2013-03-01', '2016-03-22', '2016-03-22', '2016-03-23 10:37:30', '1', null, '1', '2', '2016-03-22 22:12:51', '请填写个人邮箱');
INSERT INTO `t_student` VALUES ('4', '201409231001012', '魏嘉志', '1', '1987-01-02', '汉', '', '13200000012', '', '440901198709194316', '1', '2016-03-24', null, null, '2016-03-24 17:08:44', '1', '3', '1', '0', null, null);

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
-- Records of t_student_course
-- ----------------------------
INSERT INTO `t_student_course` VALUES ('1', '1', '1', null, '2016-03-23 14:20:07', '1');
INSERT INTO `t_student_course` VALUES ('2', '3', '2', '60', '2016-03-23 16:26:36', '1');
INSERT INTO `t_student_course` VALUES ('3', '4', '2', null, '2016-03-24 17:09:31', '1');

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

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', 'mybatis save');
INSERT INTO `test` VALUES ('2', 'mybatis-0');
INSERT INTO `test` VALUES ('3', 'mybatis-1');
INSERT INTO `test` VALUES ('4', 'mybatis-2');
INSERT INTO `test` VALUES ('5', 'mybatis-3');
INSERT INTO `test` VALUES ('6', 'mybatis-4');
INSERT INTO `test` VALUES ('7', 'mybatis-5');
INSERT INTO `test` VALUES ('8', 'mybatis-6');
INSERT INTO `test` VALUES ('9', 'mybatis-7');
INSERT INTO `test` VALUES ('10', 'mybatis-8');
INSERT INTO `test` VALUES ('11', 'mybatis-9');
INSERT INTO `test` VALUES ('12', 'mybatis-10');
INSERT INTO `test` VALUES ('13', 'mybatis-11');
INSERT INTO `test` VALUES ('14', 'mybatis-12');
INSERT INTO `test` VALUES ('15', 'mybatis-13');
INSERT INTO `test` VALUES ('16', 'mybatis-14');
INSERT INTO `test` VALUES ('17', 'mybatis-15');
INSERT INTO `test` VALUES ('18', 'mybatis-16');
INSERT INTO `test` VALUES ('19', 'mybatis-17');
INSERT INTO `test` VALUES ('20', 'mybatis-18');
INSERT INTO `test` VALUES ('21', 'mybatis-19');
