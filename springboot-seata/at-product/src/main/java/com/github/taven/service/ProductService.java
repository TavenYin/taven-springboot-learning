package com.github.taven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deduct(Integer goodsId, Integer quantity) {
        jdbcTemplate.update("update product set quantity = quantity - ? where id = ? and quantity - ? >= 0",
                quantity, goodsId, quantity);
    }

}
