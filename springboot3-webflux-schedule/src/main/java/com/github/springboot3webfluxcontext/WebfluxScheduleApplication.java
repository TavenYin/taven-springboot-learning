package com.github.springboot3webfluxcontext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@SpringBootApplication
public class WebfluxScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxScheduleApplication.class, args);
    }

    @Component
    public static class LogFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange serverWebExchange,
                                 WebFilterChain webFilterChain) {
            log.info("filter start");
            return webFilterChain.filter(serverWebExchange)
                    .doFinally(signalType -> log.info("filter end"));
        }
    }

    @GetMapping("test")
    public Mono<String> test() {
        return Mono.fromCallable(() -> "hello world");
    }

    @GetMapping("subscribeOn")
    public Mono<String> subscribeOn() {
        return Mono.fromCallable(() -> {
                    String result = "hello world";
                    log.info("{}", result);
//                    byte[] data = new byte[1024 * 1024];
                    // 填充数据
                    // ...
//                    return data;
                    return result;
                }).subscribeOn(Schedulers.boundedElastic())
                .doOnNext(result -> log.info("doOnNext: {}", result));
    }

    @GetMapping("publishOn")
    public Mono<String> publishOn() {
        return Mono.fromCallable(() -> {
                    String result = "hello world";
                    log.info("{}", result);
                    return result;
                }).subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .doOnNext(result -> log.info("contextThreadLocal: {}", result));
    }

}
