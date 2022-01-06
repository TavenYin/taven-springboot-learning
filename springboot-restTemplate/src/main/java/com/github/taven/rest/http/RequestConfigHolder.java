package com.github.taven.rest.http;

import org.apache.http.client.config.RequestConfig;

/**
 * @author tianwen.yin
 */
public class RequestConfigHolder {

    private static final ThreadLocal<RequestConfig> threadLocal = new ThreadLocal<>();

    public static void bind(RequestConfig requestConfig) {
        threadLocal.set(requestConfig);
    }

    public static RequestConfig get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
