package com.study.router;

import com.study.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> usersRoute(UserHandler handle) {

        return route()
                .nest(path("/users"), builder -> {
                    builder
                            .GET(handle::getAll)
                            .POST(handle::addUser)
                            .PUT("/{id}", handle::updateUser)
                            .DELETE("/{id}", handle::deleteUser);
                })
                .GET("/helloworld", request -> ServerResponse.ok().bodyValue("helloWorld"))
                .build();
    }
}
