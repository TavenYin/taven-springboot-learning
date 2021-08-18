package com.github.taven.webfluxfirstexp.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.HttpResources;

@Controller
public class BlockingController {
    private final static Scheduler REACTOR_HTTP_NIO_SCHEDULER =
            Schedulers.fromExecutor(HttpResources.get().onServer(true));

    public Mono<ServerResponse> block(ServerRequest request) {
        Mono<String> hello = Mono.defer(() -> {
            System.out.println("defer " + Thread.currentThread().getName());
            return Mono.just("{\"HELLO\": true}");
        })
                .map(t -> {
                    System.out.println("map1 " + Thread.currentThread().getName());
                    return t;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(REACTOR_HTTP_NIO_SCHEDULER)
                .map(t -> {
                    System.out.println("map2 " + Thread.currentThread().getName());
                    return t;
                })
                ;

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(hello, String.class));
    }

    @Configurable
    public static class BlockingControllerRouter {
        @Bean
        public RouterFunction<ServerResponse> blockingRouter(BlockingController blockingController) {
            return RouterFunctions.route(
                    RequestPredicates.GET("/block")
                            .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), blockingController::block
            );
        }
    }

}
