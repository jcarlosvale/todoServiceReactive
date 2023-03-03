package com.study.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TodoRouter {

    @Bean
    public RouterFunction<ServerResponse> todosRouter() {
        return route()
                .GET("/v2/helloworld", request -> ServerResponse.ok().bodyValue("hello world"))
                .build();
    }
}
