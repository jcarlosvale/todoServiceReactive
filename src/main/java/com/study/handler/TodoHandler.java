package com.study.handler;

import com.study.domain.TodoInfo;
import com.study.repository.TodoInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TodoHandler {

    private final TodoInfoRepository repository;
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TodoInfo.class)
                .flatMap(repository::save)
                .flatMap(review -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(review));
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        var date = serverRequest.queryParam("date");

        if(date.isPresent()) {
            var todos = repository.findByTodoDate(LocalDate.parse(date.get()));
            return ServerResponse.ok().body(todos, TodoInfo.class);
        } else {
            var todos = repository.findAll();
            return ServerResponse.ok().body(todos, TodoInfo.class);
        }
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        var existingMono = repository.findById(id);

        return existingMono
                .flatMap(persisted -> serverRequest.bodyToMono(TodoInfo.class)
                        .map(request -> {
                            persisted.setDescription(request.getDescription());
                            persisted.setTodoDate(request.getTodoDate());
                            return persisted;
                        }))
                .flatMap(repository::save)
                .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        var existing = repository.findById(id);

        return existing
                .flatMap(review -> repository.deleteById(id))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");

        return repository
                .findById(id)
                .flatMap(todoInfo -> ServerResponse.ok().bodyValue(todoInfo));
    }
}
