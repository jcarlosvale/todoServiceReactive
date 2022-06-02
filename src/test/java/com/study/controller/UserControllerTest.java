package com.study.controller;

import com.study.model.User;
import com.study.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTest {

    private static final String URL = "/v1/users";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setup() {
        var users =
        List.of(
                User.builder().username("jose").name("jose paulo").todos(List.of("Correr", "Estudar")).build(),
                User.builder().username("maria").name("maria luiza").todos(List.of("Acordar", "Estudar")).build(),
                User.builder().username("joao").name("joao mario").todos(List.of("Brincar", "Dormir", "Viajar")).build()
        );

        repository.saveAll(users);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
    }

    @Test
    void retrieveAll() {
        //GIVEN
        List<User> expectedList = List.of(
                User.builder().username("jose").name("jose paulo").todos(List.of("Correr", "Estudar")).build(),
                User.builder().username("maria").name("maria luiza").todos(List.of("Acordar", "Estudar")).build(),
                User.builder().username("joao").name("joao mario").todos(List.of("Brincar", "Dormir", "Viajar")).build()
        );

        //WHEN //THEN
        webTestClient
                .get()
                .uri(URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .consumeWith(listEntityExchangeResult -> {
                    var list = listEntityExchangeResult.getResponseBody();
                    assertThat(list).hasSameElementsAs(expectedList);
                });
    }


}