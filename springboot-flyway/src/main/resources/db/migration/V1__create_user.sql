CREATE TABLE `sys_user` (
  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `real_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型（保留字段）',
  `is_ban` tinyint(1) DEFAULT NULL COMMENT '封号状态(0-有效，1-封号)',
  `is_del` tinyint(1) NOT NULL COMMENT '0-未删除，1-删除',
  `create_user` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_user` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
