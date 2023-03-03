package com.study.handler;

import com.study.domain.TodoInfo;
import com.study.exceptionhandler.TodoInfoDataException;
import com.study.exceptionhandler.TodoInfoNotFoundException;
import com.study.repository.TodoInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TodoHandler {

    private final TodoInfoRepository repository;

    private final Validator validator;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TodoInfo.class)
                .doOnNext(this::validate)
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

        var existingMono =
                repository.findById(id)
                        .switchIfEmpty(Mono.error(new TodoInfoNotFoundException(" TodoInfo not found to given id " + id)));

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

    private void validate(final TodoInfo todoInfo) {

        var constraints = validator.validate(todoInfo);
        log.error("Violations : {}", constraints);

        if (!constraints.isEmpty()) {
            var errorMessages = constraints.stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(","));
            throw new TodoInfoDataException(errorMessages);
        }
    }

}
