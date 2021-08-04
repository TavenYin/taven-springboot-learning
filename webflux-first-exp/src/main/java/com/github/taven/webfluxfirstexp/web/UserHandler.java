package com.github.taven.webfluxfirstexp.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    public Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                         .body(BodyInserters.fromValue("Hello, Spring!"));
    }

    @Configurable
    public static class UserRouter {
        @Bean
        public RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
            return RouterFunctions.route(
                    RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), userHandler::helloWorld
            );
        }
    }

}
