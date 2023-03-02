package com.study.controller;


import com.study.domain.TodoInfo;
import com.study.service.TodoInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/todoinfos")
public class TodosInfoController {

    private final TodoInfoService todoInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TodoInfo> save(@RequestBody TodoInfo todoInfo) {
        return todoInfoService.addTodoInfo(todoInfo).log();
    }

    @GetMapping
    public Flux<TodoInfo> getAll() {
        return todoInfoService.getAllTodoInfos().log();
    }

    @GetMapping("/{id}")
    public Mono<TodoInfo> getById(@PathVariable String id) {
        return todoInfoService.getTodoInfoById(id).log();
    }

    @PutMapping("/{id}")
    public Mono<TodoInfo> update(@RequestBody TodoInfo updatedTodoInfo, @PathVariable String id) {
        return todoInfoService.updateTodoInfo(updatedTodoInfo, id).log();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return todoInfoService.deleteTodoInfo(id).log();
    }

}