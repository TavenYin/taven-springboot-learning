package com.github.taven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.UUID;

@Service
@CacheConfig(cacheNames="demoCache")
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

    @Transactional
    @Override
    public void insertDBAndRedis(boolean rollback) {
        String sql = "INSERT INTO sys_user(`id`, `username`) VALUES (?, ?)";
        jdbcTemplate.update(sql, uuid(), "taven");
        redisTemplate.opsForValue().set(uuid(), "taven");
        rollbackIfNecessary(rollback);
    }

    @Cacheable(key = "#uuid")
    @Override
    public Object cache(String uuid) {
        return "test";
    }

    @CacheEvict(beforeInvocation = true, key = "#uuid")
    @Transactional
    @Override
    public void removeCache(boolean rollback, String uuid) {
        // 通过抛出的异常栈可以看出，缓存拦截器在事务拦截器外层，缓存的操作不受事务控制
        rollbackIfNecessary(rollback);
    }

    @CacheEvict(key = "#uuid")
    @Override
    public void removeCacheAfter(boolean rollback, String uuid) {
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
