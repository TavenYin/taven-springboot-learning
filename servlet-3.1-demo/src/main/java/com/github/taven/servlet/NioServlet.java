package com.github.taven.servlet;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@WebServlet(name="NioServlet", urlPatterns="/nio", asyncSupported = true)
public class NioServlet extends HttpServlet {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(8);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.startAsync();
        System.out.println("NioServlet service thread is " + Thread.currentThread().getName());

        final ServletInputStream servletInputStream = req.getInputStream();
        servletInputStream.setReadListener(new NioReadListener(asyncContext, req, resp));
    }

    private static class NioReadListener implements ReadListener {
        AsyncContext asyncContext;
        HttpServletRequest req;
        HttpServletResponse resp;

        public NioReadListener(AsyncContext asyncContext, HttpServletRequest req, HttpServletResponse resp) {
            this.asyncContext = asyncContext;
            this.req = req;
            this.resp = resp;
        }

        @Override
        public void onDataAvailable() throws IOException {
            ServletInputStream servletInputStream = asyncContext.getRequest().getInputStream();

            int readBytes = 0;
            byte[] buffer = new byte[1024 * 32];
            while (servletInputStream.isReady() && !servletInputStream.isFinished()) {
                readBytes += servletInputStream.read(buffer, readBytes, buffer.length);
            }

            buffer = Arrays.copyOf(buffer, readBytes);
            System.out.println(new String(buffer, StandardCharsets.UTF_8));
        }

        @Override
        public void onAllDataRead() throws IOException {
            POOL.execute(() -> {
                try {
                    // 业务代码 ...
                    TimeUnit.SECONDS.sleep(1);

                    resp.setContentType("text/json;charset=UTF-8");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getOutputStream().setWriteListener(new NioWriteListener(asyncContext, resp));
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static class NioWriteListener implements WriteListener {
        AsyncContext asyncContext;
        HttpServletResponse resp;

        public NioWriteListener(AsyncContext asyncContext, HttpServletResponse resp) {
            this.asyncContext = asyncContext;
            this.resp = resp;
        }

        @Override
        public void onWritePossible() throws IOException {
            resp.getOutputStream().print(JSONObject.toJSONString(Map.of("name", "NioServlet")));
            resp.getOutputStream().flush();
            asyncContext.complete();
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
