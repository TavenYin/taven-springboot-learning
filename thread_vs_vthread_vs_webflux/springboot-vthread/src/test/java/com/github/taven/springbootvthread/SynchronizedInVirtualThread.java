package com.github.taven.springbootvthread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SynchronizedInVirtualThread {

    public static class SyncClass {
        ReentrantLock lock = new ReentrantLock();

        public synchronized void syncMethod() {
            log.info("{} Start syncMethod", Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("{} End syncMethod", Thread.currentThread());
        }

        public void lockMethod() {
            lock.lock();
            try {
                log.info("{} Start lockMethod", Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("{} End lockMethod", Thread.currentThread());
            } finally {
                lock.unlock();
            }
        }

        public void normalMethod() {
            log.info("{} Start normalMethod", Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            log.info("{} End normalMethod", Thread.currentThread());
        }
    }

    /**
     * 结论：
     * 目前在 java21 中
     * 1. 当虚拟线程的代码执行到 synchronized 代码块时，会导致虚拟线程所在的平台线程 Block（该平台线程无法执行其他虚拟线程）
     * 2. 将 synchronized 替换为 ReentrantLock 后，该问题得到解决
     */
    public static void main(String[] args) {
        ThreadFactory tf = Thread.ofVirtual().factory();
        SyncClass syncClass = new SyncClass();
        // 虚拟线程能使用的平台线程数=CPU核心数
        // 这里的循环次数需要跑满所有的平台线程
        for (int i=0; i<20; i++) {
            // 分别注释掉这两行，查看运行结果
            Thread thread = tf.newThread(syncClass::syncMethod);
//            Thread thread = tf.newThread(syncClass::lockMethod);
            thread.start();
        }

        try {
            // 等第一波 task 先执行后，再执行第二波 task
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        // 验证虚拟线程对应的平台线程没有被 block
        for (int i=0; i<20; i++) {
            Thread thread = tf.newThread(syncClass::normalMethod);
            thread.start();
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
