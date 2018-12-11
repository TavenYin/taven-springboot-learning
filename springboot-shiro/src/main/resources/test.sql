/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 11/12/2018 21:42:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1-目录，2-菜单，3按钮',
  `permission_order` int(11) NULL DEFAULT NULL,
  `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '0-未删除，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifyUser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('001', '系统管理', '1', 1, '0', '', 0, NULL, NULL, '2018-12-09 17:46:47', NULL);
INSERT INTO `sys_permission` VALUES ('002', '用户管理', '2', 1, '001', '/user/index.html', 0, NULL, NULL, '2018-12-09 17:59:41', NULL);
INSERT INTO `sys_permission` VALUES ('0021', '新增', '3', 1, '002', NULL, 0, NULL, NULL, '2018-12-09 17:50:10', NULL);
INSERT INTO `sys_permission` VALUES ('0022', '编辑', '3', 2, '002', NULL, 0, NULL, NULL, '2018-12-09 17:50:31', NULL);
INSERT INTO `sys_permission` VALUES ('0023', '冻结', '3', 3, '002', NULL, 0, NULL, NULL, '2018-12-09 17:50:49', NULL);
INSERT INTO `sys_permission` VALUES ('003', '角色管理', '2', 2, '001', '/role/index.html', 0, NULL, NULL, '2018-12-09 17:59:51', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '0-未删除，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifyUser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('000000', '超级管理员', 'super_admin', 0, NULL, NULL, '2018-12-09 16:35:45', NULL);
INSERT INTO `sys_role` VALUES ('100000', '游客', 'visitor', 0, NULL, NULL, '2018-12-09 16:36:28', NULL);

-- ----------------------------
-- Table structure for sys_role_permission_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_ref`;
CREATE TABLE `sys_role_permission_ref`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '0-未删除，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifyUser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission_ref
-- ----------------------------
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431558', '000000', '001', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431559', '000000', '002', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431560', '000000', '0021', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431561', '000000', '0022', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431562', '000000', '0023', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);
INSERT INTO `sys_role_permission_ref` VALUES ('97967221665431563', '000000', '003', 0, NULL, NULL, '2018-12-09 18:10:37', NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '保留字段 用户类型',
  `is_lock` tinyint(4) NULL DEFAULT NULL COMMENT '0-未锁定，1-锁定',
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '0-未删除，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifyUser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'taven', 'f16c8825bb9369d4b533bfe3850da55d', NULL, 0, 0, NULL, NULL, '2018-12-09 16:33:32', NULL);
INSERT INTO `sys_user` VALUES ('2', 'hmj', 'xxx', NULL, 0, 0, NULL, NULL, '2018-12-09 16:33:35', NULL);

-- ----------------------------
-- Table structure for sys_user_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_ref`;
CREATE TABLE `sys_user_role_ref`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '0-未删除，1-删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modifyUser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
