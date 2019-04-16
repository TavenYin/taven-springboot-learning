package com.gitee.taven;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class RunTest implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunTest.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i=0; i<15; i++) {
            Thread thread1 = new TestThread("thread" + i);
            thread1.start();
            Thread.sleep(100);
        }
    }

    private class TestThread extends Thread {

        public TestThread(String name) {
            super(name);
        }

        @Override
        public void run() {
//            RLock lock = redissonClient.getLock("lock");
            RLock lock = redissonClient.getFairLock("lock");
//            LOGGER.info(Thread.currentThread().getName() + " tryLock");
            lock.lock(2, SECONDS);

            if (lock.isHeldByCurrentThread()) {
                try {
                    LOGGER.info(Thread.currentThread().getName() + " getLock");
                    Thread.sleep(5000);
                    LOGGER.info(Thread.currentThread().getName() + " 业务执行完成");

                } catch (InterruptedException e) {

                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                        LOGGER.info(Thread.currentThread().getName() + " unlock");

                    } else {
                        LOGGER.info(Thread.currentThread().getName() + " already automatically release lock");
                    }

                }

            } else {
                LOGGER.info(Thread.currentThread().getName() + " lock fail");
            }
        }
    }

}