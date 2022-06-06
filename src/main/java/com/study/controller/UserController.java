package com.study.controller;

import com.study.model.User;
import com.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<User>> getUserById(@PathVariable(name="id") final String username){

        return service
                .retrieveById(username)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable(name="id") final String username) {

        return service
                .delete(username)
                .map(aBoolean -> {
                    if (aBoolean) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                    else return ResponseEntity.notFound().build();
                });
    }


}
