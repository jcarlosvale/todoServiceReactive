package com.study.handler;

import com.study.model.User;
import com.study.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandle {

    private final UserRepository repository;

    public Mono<ServerResponse> getAll() {
        var flux = repository.findAll();
        return ServerResponse.ok().body(flux, User.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {

        return request
                .bodyToMono(User.class)
                .flatMap(repository::save)
                .flatMap(savedUser ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(savedUser));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        return repository
                .deleteById(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        var userFromDatabase = repository.findById(id);

        return
                userFromDatabase
                        .flatMap(userDb ->
                                serverRequest.bodyToMono(User.class)
                                        .map(userDto -> {
                                            userDb.setName(userDto.getName());
                                            userDb.setTodos(userDto.getTodos());
                                            return userDb;
                                        })
                                        .flatMap(repository::save)
                                        .flatMap(savedUser -> ServerResponse
                                                .status(HttpStatus.CREATED)
                                                .bodyValue(savedUser))
                        )
                        .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }
}
