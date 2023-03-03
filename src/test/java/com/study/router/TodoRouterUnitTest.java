package com.study.router;

import com.study.domain.TodoInfo;
import com.study.exceptionhandler.GlobalErrorHandler;
import com.study.handler.TodoHandler;
import com.study.repository.TodoInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {TodoRouter.class, TodoHandler.class, GlobalErrorHandler.class})
@AutoConfigureWebTestClient
class TodoRouterUnitTest {

    public static final String URL = "/v2/todoinfos";

    @MockBean
    private TodoInfoRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void save() {
        //given
        var newTodo = new TodoInfo(null, "Homework 1", LocalDate.parse("2008-07-18"));

        when(repository.save(newTodo))
                .thenReturn(Mono.just(new TodoInfo("abc", "Homework 1", LocalDate.parse("2008-07-18"))));

        //when
        webTestClient
                .post()
                .uri(URL)
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
    }

    @Test
    void saveValidation() {
        //given
        var newTodo = new TodoInfo(null, "", LocalDate.parse("2008-07-18"));

        when(repository.save(newTodo))
                .thenReturn(Mono.just(new TodoInfo("abc", "Homework 1", LocalDate.parse("2008-07-18"))));

        //when
        webTestClient
                .post()
                .uri(URL)
                .bodyValue(newTodo)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .isEqualTo("description must be present");
    }
}