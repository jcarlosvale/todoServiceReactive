package com.study.controller;

import com.study.model.User;
import com.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService service;


    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> retrieveAll() {

        return service.retrieveAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody final User user) {

        return service.saveUser(user);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> getUserById(@PathVariable final String userName){
        return null;
    }

}
