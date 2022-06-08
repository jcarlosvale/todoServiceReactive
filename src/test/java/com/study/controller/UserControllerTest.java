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

    private static final String URL = "/users";

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

        repository.saveAll(users).blockLast();
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


    @Test
    void saveUser() {
        //GIVEN
        User newUser = User.builder().username("roberta").name("Roberta Rebeca").todos(List.of("Estudar")).build();

        //WHEN //THEN
        webTestClient
                .post()
                .uri(URL)
                .bodyValue(newUser)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .consumeWith(result -> {
                    var response = result.getResponseBody();
                    assertThat(response).isEqualTo(newUser);
                });
    }

    @Test
    void findById() {

        //GIVEN
        var username = "maria";
        var expected = User.builder().username("maria").name("maria luiza").todos(List.of("Acordar", "Estudar")).build();

        //WHEN //THEN
        webTestClient
                .get()
                .uri(URL + "/{id}", username)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .consumeWith(entityExchangeResult -> {
                    var actual = entityExchangeResult.getResponseBody();
                    assertThat(actual).isEqualTo(expected);
                });
    }

    @Test
    void findById_NotFound() {

        //GIVEN
        var username = "zezim";

        //WHEN //THEN
        webTestClient
                .get()
                .uri(URL + "/{id}", username)
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    void delete() {

        //GIVEN
        var username = "maria";

        //WHEN //THEN
        webTestClient
                .delete()
                .uri(URL + "/{id}", username)
                .exchange()
                .expectStatus()
                .isNoContent();
    }


    @Test
    void delete_NOT_FOUND() {

        //GIVEN
        var username = "zezim";

        //WHEN //THEN
        webTestClient
                .delete()
                .uri(URL + "/{id}", username)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void update() {

        //GIVEN
        var username = "joao";
        var expected = User.builder().username("joao").name("jcarlos").todos(List.of("Estudar")).build();

        //WHEN THEN
        webTestClient
                .put()
                .uri(URL + "/{id}", username)
                .bodyValue(expected)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(User.class)
                .consumeWith(entityExchangeResult -> {
                    var actual = entityExchangeResult.getResponseBody();
                    assertThat(actual).isEqualTo(expected);
                });
    }

    @Test
    void update_NOTFOUND() {

        //GIVEN
        var username = "zezim";
        var emptyValue = User.builder().build();

        //WHEN THEN
        webTestClient
                .put()
                .uri(URL + "/{id}", username)
                .bodyValue(emptyValue)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}