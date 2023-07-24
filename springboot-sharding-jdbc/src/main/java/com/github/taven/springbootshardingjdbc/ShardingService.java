package com.github.taven.springbootshardingjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShardingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public ShardingService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public void insertOrder(Long orderId, Long userId) {
        String sql = "insert into t_order(order_id, user_id, create_time) values(?,?,now())";
        jdbcTemplate.update(sql, orderId, userId);
    }

}
