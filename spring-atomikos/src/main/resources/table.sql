CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `user_informations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `email` varchar(32) DEFAULT NULL COMMENT '邮件',
  `address` varchar(32) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;