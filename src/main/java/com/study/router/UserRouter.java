package com.study.router;

import com.study.handler.UserHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRouters(UserHandle handle) {
        return route()
                .nest(path("/users"), builder -> {
                        builder
                            .GET(request -> handle.getAll())
                            .POST(handle::save)
                            .PUT("/{id}", handle::update)
                            .DELETE("/{id}", handle::delete);
                })

                .GET("/helloWorld", request -> ServerResponse.ok().bodyValue("hello world Router"))
                .build();
    }
}
