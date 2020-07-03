CREATE TABLE `sys_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL COMMENT '发送者',
  `receiver_id` int(11) DEFAULT NULL COMMENT '接收者',
  `msg` varchar(500) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL COMMENT '0-未读，1-已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;