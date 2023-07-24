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

    public void insertOrder(Long userId) {

    }

}
