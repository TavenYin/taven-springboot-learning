package com.gitee.taven;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient =Redisson.create(config);

        RLock lock = redissonClient.getLock("test");
        lock.tryLock(10, 10, TimeUnit.SECONDS);
        new Thread(() -> {
            try {
                lock.tryLock(10, 10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("👴完事了");
            lock.unlock();

        }).start();

        try {
            System.out.println("执行主线程逻辑");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();
        System.out.println("主线程解锁");
    }
}
