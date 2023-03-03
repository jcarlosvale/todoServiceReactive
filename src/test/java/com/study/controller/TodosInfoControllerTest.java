package com.study.controller;

import com.study.domain.TodoInfo;
import com.study.repository.TodoInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")  //new versions of Mongo broken the Embedded
@AutoConfigureWebTestClient
class TodosInfoControllerTest {

    public static final String TODO_INFOS_URL = "/v1/todoinfos";
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TodoInfoRepository todoInfoRepository;

    @BeforeEach
    void setup() {
        var todoinfos = List.of(
                new TodoInfo(null, "Homework", LocalDate.parse("2008-07-18")),
                new TodoInfo(null, "Gym", LocalDate.parse("2020-06-11")),
                new TodoInfo("abc", "Wash my car", LocalDate.parse("2012-07-20"))
        );

        todoInfoRepository
                .saveAll(todoinfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        todoInfoRepository
                .deleteAll()
                .block();
    }

    @Test
    void save() {
        //given
        var newTodo = new TodoInfo(null, "Homework 1", LocalDate.parse("2008-07-18"));

        //when
        webTestClient
                .post()
                .uri(TODO_INFOS_URL)
                .bodyValue(newTodo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(TodoInfo.class)
                .consumeWith(todoInfoEntityExchangeResult -> {

                    var saved = todoInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(saved);
                    assertNotNull(saved.getTodoInfoId());
                });

        //then
    }

    @Test
    void getAll() {
        webTestClient
                .get()
                .uri(TODO_INFOS_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(TodoInfo.class)
                .hasSize(3);
    }

    @Test
    void getByDate() {

        var uri = UriComponentsBuilder.fromUriString(TODO_INFOS_URL)
                        .queryParam("date", LocalDate.parse("2020-06-11"))
                                .buildAndExpand().toUri();

        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(TodoInfo.class)
                .hasSize(1);
    }

    @Test
    void getById() {
        var id = "abc";

        webTestClient
                .get()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(TodoInfo.class)
                .consumeWith(todoInfoEntityExchangeResult -> {
                    var result = todoInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(result);
                });
    }

    @Test
    void getByIdJsonPath() {
        var id = "abc";

        webTestClient
                .get()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.description").isEqualTo("Wash my car");
    }

    @Test
    void update() {
        //given
        var id = "abc";
        var updateTodo = new TodoInfo(null, "Homework 1", LocalDate.parse("2008-07-18"));

        //when
        webTestClient
                .put()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .bodyValue(updateTodo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(TodoInfo.class)
                .consumeWith(todoInfoEntityExchangeResult -> {

                    var updated = todoInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(updated);
                    assertNotNull(updated.getTodoInfoId());
                    assertEquals("Homework 1", updated.getDescription());
                });

        //then
    }

    @Test
    void updateNotFound() {
        //given
        var id = "xyz";
        var updateTodo = new TodoInfo(null, "Homework 1", LocalDate.parse("2008-07-18"));

        //when
        webTestClient
                .put()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .bodyValue(updateTodo)
                .exchange()
                .expectStatus()
                .isNotFound();

        //then
    }

    @Test
    void delete() {
        //given
        var id = "abc";

        webTestClient
                .delete()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}