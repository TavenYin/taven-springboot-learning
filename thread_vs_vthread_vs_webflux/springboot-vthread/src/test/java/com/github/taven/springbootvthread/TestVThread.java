package com.github.taven.springbootvthread;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class TestVThread {

    public static void main(String[] args) {
        // 创建虚拟线程的方式
        Thread.ofVirtual().start(() -> log.info("{}", Thread.currentThread()));

        // 使用线程池的方式和手动 new 一个虚拟线程是一样的
        // 因为这里使用的线程池也是“每任务每线程”的线程池
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();



        ThreadFactory tf = Thread.ofVirtual().factory();
        for (int i=0; i<60; i++) {
            Thread thread = tf.newThread(() -> {
                log.info("{} Start virtual thread...", Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("{} End virtual thread...", Thread.currentThread());
            });
            thread.start();
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
