package com.study.controller;

import com.study.model.User;
import com.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService service;


    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> retrieveAll() {
        return service.retrieveAll().log();
    }
}
