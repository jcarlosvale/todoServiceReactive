package com.study.service;

import com.study.model.UserDocument;
import com.study.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Mono<UserDocument> addUser(final UserDocument document) {
        return repository.save(document);
    }

    public Flux<UserDocument> getAllDocuments() {
        return repository.findAll();
    }

    public Mono<UserDocument> getDocumentById(final String id) {
        return repository.findById(id);
    }

    public Mono<UserDocument> updateUser(String id, UserDocument document) {

        return repository
                .findById(id)
                .flatMap(userDocument -> {

                    userDocument.setName(document.getName());
                    userDocument.setTodos(document.getTodos());
                    return repository.save(userDocument);
                });

    }

    public Mono<Void> deleteUser(String id) {
        return repository.deleteById(id);
    }
}
