package com.github.taven.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@WebServlet(name="AsyncServlet", urlPatterns="/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(8);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        System.out.println("AsyncServlet service thread is " + Thread.currentThread().getName());

        POOL.execute(() -> {
            try {
                // 业务代码 ...
                TimeUnit.SECONDS.sleep(1);

                // 响应客户端
                ServletUtil.writeJson(resp, Map.of("name", "AsyncServlet"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        });
    }
}
