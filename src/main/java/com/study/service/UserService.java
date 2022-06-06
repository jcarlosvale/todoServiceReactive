package com.study.service;

import com.study.model.User;
import com.study.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;

    public Flux<User> retrieveAll() {

        return repository.findAll().log();
    }

    public Mono<User> saveUser(final User user) {

        return repository.save(user);
    }

    public Mono<User> retrieveById(String userName) {

        return repository.findById(userName);
    }

    public Mono<Boolean> delete(String username) {

        return
        repository
                .findById(username)
                .flatMap(user -> repository.delete(user).map(aUser ->true));
               // .switchIfEmpty(Mono.just(false)).log();

        //.switchIfEmpty(Mono.error(() -> new RuntimeException("Usuario Nao Encontrado")));

    }
}
