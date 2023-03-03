package com.study.repository;

import com.study.domain.TodoInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TodoInfoRepository extends ReactiveMongoRepository<TodoInfo, String> {

    Flux<TodoInfo> findByTodoDate(final LocalDate date);
}
