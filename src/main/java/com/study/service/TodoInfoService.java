package com.study.service;

import com.study.domain.TodoInfo;
import com.study.repository.TodoInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TodoInfoService {

    private final TodoInfoRepository todoInfoRepository;

    public Mono<TodoInfo> addTodoInfo(TodoInfo todoInfo) {
        return todoInfoRepository.save(todoInfo);
    }

    public Flux<TodoInfo> getAllTodoInfos() {
        return todoInfoRepository.findAll();
    }

    public Mono<TodoInfo> getTodoInfoById(String id) {
        return todoInfoRepository.findById(id);
    }

    public Mono<TodoInfo> updateTodoInfo(TodoInfo updatedTodoInfo, String id) {

        return todoInfoRepository
                .findById(id)
                .flatMap(todoInfo -> {
                    todoInfo.setDescription(updatedTodoInfo.getDescription());
                    todoInfo.setTodoDate(updatedTodoInfo.getTodoDate());

                    return todoInfoRepository.save(todoInfo);
                });
    }

    public Mono<Void> deleteTodoInfo(String id) {
        return todoInfoRepository.deleteById(id);
    }

    public Flux<TodoInfo> getByDate(LocalDate date) {
        return todoInfoRepository.findByTodoDate(date);
    }
}
