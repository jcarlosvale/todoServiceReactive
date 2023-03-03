package com.study.router;

import com.study.handler.TodoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TodoRouter {

    @Bean
    public RouterFunction<ServerResponse> todosRouter(final TodoHandler todoHandler) {
        return route()
                .nest(path("/v2/todoinfos"), builder ->
                        builder
                                .POST("", todoHandler::save)
                                .GET("", todoHandler::get)
                                .GET("/{id}", todoHandler::getById)
                                .PUT("/{id}", todoHandler::update)
                                .DELETE("/{id}", todoHandler::delete)
                )
//                .POST("/v2/todoinfos", todoHandler::save)
//                .GET("/v2/todoinfos", todoHandler::get)
                .GET("/v2/helloworld", request -> ServerResponse.ok().bodyValue("hello world"))
                .build();
    }
}
