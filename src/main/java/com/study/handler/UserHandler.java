package com.study.handler;

import com.study.model.UserDocument;
import com.study.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository repository;

    public Mono<ServerResponse> addUser(ServerRequest request) {
        return request.bodyToMono(UserDocument.class)
                .flatMap(repository::save)
                .flatMap(savedUser -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedUser));
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        var flux = repository.findAll();
        return ServerResponse.ok().body(flux, UserDocument.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        var userFromDatabase = repository.findById(id);

        return userFromDatabase
                .flatMap(userDocument -> serverRequest.bodyToMono(UserDocument.class)
                                .map(userFromBody -> {
                                    userDocument.setName(userFromBody.getName());
                                    userDocument.setTodos(userFromBody.getTodos());
                                    return userDocument;
                                })
                                .flatMap(repository::save)
                                .flatMap(savedUser -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedUser))
                );
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {

        var id = serverRequest.pathVariable("id");

        return repository.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
