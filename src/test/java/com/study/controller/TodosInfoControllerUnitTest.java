package com.study.controller;


import com.study.domain.TodoInfo;
import com.study.service.TodoInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = TodosInfoController.class)
@AutoConfigureWebTestClient
class TodosInfoControllerUnitTest {

    public static final String TODO_INFOS_URL = "/v1/todoinfos";

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private TodoInfoService todoInfoService;

    @Test
    void getAll() {

        var todoinfos = List.of(
                new TodoInfo(null, "Homework", LocalDate.parse("2008-07-18")),
                new TodoInfo(null, "Gym", LocalDate.parse("2020-06-11")),
                new TodoInfo("abc", "Wash my car", LocalDate.parse("2012-07-20"))
        );

        when(todoInfoService.getAllTodoInfos())
                .thenReturn(Flux.fromIterable(todoinfos));

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
    void getById() {
        var todoInfo = new TodoInfo("abc", "Wash my car", LocalDate.parse("2012-07-20"));
        var id = "abc";

        when(todoInfoService.getTodoInfoById(id))
                .thenReturn(Mono.just(todoInfo));

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
    void save() {
        //given
        var newTodo = new TodoInfo("mockId", "Wash my car", LocalDate.parse("2012-07-20"));

        when(todoInfoService.addTodoInfo(newTodo))
                .thenReturn(Mono.just(newTodo));

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
                    assertEquals("mockId", saved.getTodoInfoId());
                });

        //then
    }

    @Test
    void saveWithValidation() {
        //given
        var newTodo = new TodoInfo("mockId", "", LocalDate.parse("2012-07-20"));

        //when
        webTestClient
                .post()
                .uri(TODO_INFOS_URL)
                .bodyValue(newTodo)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    var responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals("description must be present", responseBody);
                });

        //then
    }

    @Test
    void update() {
        //given
        var id = "abc";
        var updateTodo = new TodoInfo("mockId", "Wash my car 1", LocalDate.parse("2012-07-20"));

        when(todoInfoService.updateTodoInfo(updateTodo, id))
                .thenReturn(Mono.just(updateTodo));

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
                    assertEquals("mockId", updated.getTodoInfoId());
                    assertEquals("Wash my car 1", updated.getDescription());
                });

        //then
    }

    @Test
    void delete() {
        //given
        var id = "abc";

        when(todoInfoService.deleteTodoInfo(id))
                .thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri(TODO_INFOS_URL + "/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}