package com.github.taven.service;

import io.seata.tm.api.TransactionalTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    TransactionalTemplate

    @Transactional
    public void createOrder(Integer goodsId, Integer quantity, boolean testRollback) {
        jdbcTemplate.update("insert into `order` (`sn`, `price`, `create_time`) VALUES(?,?,?)",
                String.valueOf(System.currentTimeMillis()), 1, System.currentTimeMillis() / 1000);

        Integer orderId = jdbcTemplate.queryForObject("select LAST_INSERT_ID() ", Integer.class);


        jdbcTemplate.update("insert into `order_item` (`order_id`, `goods_id`, `quantity`) VALUES(?,?,?)",
                orderId, goodsId, quantity);

        if (testRollback) {
            throw new RuntimeException("testRollback");
        }
    }

}
