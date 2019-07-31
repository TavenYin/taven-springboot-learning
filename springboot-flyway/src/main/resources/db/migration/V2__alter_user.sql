ALTER TABLE `sys_user`
MODIFY COLUMN `real_name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL AFTER `password`;
