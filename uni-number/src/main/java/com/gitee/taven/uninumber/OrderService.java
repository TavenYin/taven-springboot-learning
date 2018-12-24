package com.gitee.taven.uninumber;

import com.gitee.taven.uninumber.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService {

    private static OrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    private static final int TOTAL_THREADS = 100;

    public void multiThread() {
        // 创建固定长度线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < TOTAL_THREADS; i++) {
            Thread thread = new OrderThread();
            fixedThreadPool.execute(thread);
        }

    }

    public static class OrderThread extends Thread {

        @Override
        public void run() {
            try {
                Map<String, String> parameterMap = initParameterMap();
                orderMapper.createOrderNum(parameterMap);
                String number = parameterMap.get("result");
                System.out.println(Thread.currentThread().getName() + " : " +number);

            } catch (Exception e) {
                System.out.println(e);

            }
        }

        public Map<String, String> initParameterMap() {
            Map<String, String> parameterMap = new HashMap<>();
            parameterMap.put("tsCode", "order");
            parameterMap.put("result", "-1");
            return parameterMap;
        }

    }

}
