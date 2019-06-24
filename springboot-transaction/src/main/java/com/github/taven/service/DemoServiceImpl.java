package com.github.taven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.UUID;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public void insertDB(boolean rollback) {
        String sql = "INSERT INTO sys_user(`id`, `username`) VALUES (?, ?)";
        jdbcTemplate.update(sql, uuid(), "taven");
        rollbackIfNecessary(rollback);
    }

    /**
     * 事务相关详见{@link TransactionAspectSupport}
     *
     * @param rollback
     */
    @Transactional
    @Override
    public void insertRedis(boolean rollback) {
        redisTemplate.opsForValue().set(uuid(), "taven");
        rollbackIfNecessary(rollback);
    }

    private void rollbackIfNecessary(boolean rollback) {
        if (rollback) {
            throw new RuntimeException();
        }
    }

    private String uuid() {
        return UUID.randomUUID().toString();
    }
}
