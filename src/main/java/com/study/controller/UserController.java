package com.study.controller;

import com.study.model.UserDocument;
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

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDocument> addUser(@RequestBody final UserDocument document) {

        return this.service.addUser(document).log();
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDocument> getAll(){

        return service.getAllDocuments().log();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDocument> getDocumentById(@PathVariable final String id){

        return service.getDocumentById(id).log();
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDocument> updateDocument(@PathVariable final String id, @RequestBody final UserDocument document) {

        return this.service.updateUser(id, document);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDocument(@PathVariable final String id) {

        return this.service.deleteUser(id);
    }


}
