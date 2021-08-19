package com.github.taven.webfluxfirstexp.utils;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.HttpResources;

public class SpringReactiveUtils {
    private final static Scheduler REACTOR_HTTP_NIO_SCHEDULER =
            Schedulers.fromExecutor(HttpResources.get().onServer(true));

    public static Scheduler reactorHttpNioScheduler() {
        return REACTOR_HTTP_NIO_SCHEDULER;
    }
}
