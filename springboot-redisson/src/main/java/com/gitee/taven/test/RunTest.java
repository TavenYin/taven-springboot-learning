package com.gitee.taven.test;

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
            Thread thread1 = new Thread(new TestThread(i));
            thread1.start();
            Thread.sleep(100);
        }
    }

    private class TestThread implements Runnable {
        private int index;

        public TestThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
//            RLock lock = redissonClient.getLock("lock");
            RLock lock = redissonClient.getFairLock("lock");
            LOGGER.info("Thread" + index + " tryLock");
            lock.lock(10, SECONDS);
            LOGGER.info("Thread" + index + " lock");
            try {
                Thread.sleep(2000);
                LOGGER.info("Thread" + index + " 业务执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isLocked())
                    lock.unlock();
                LOGGER.info("Thread" + index + " unlock");
            }
        }
    }

}