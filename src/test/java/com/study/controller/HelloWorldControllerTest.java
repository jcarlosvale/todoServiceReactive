package com.study.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(controllers = HelloWorldController.class)
@AutoConfigureWebTestClient
class HelloWorldControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux() {
        webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void fluxContent() {
        var flux = webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(flux)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    void fluxContentV2() {
         webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                 .consumeWith(integerFluxExchangeResult -> {
                     var responseBody = integerFluxExchangeResult.getResponseBody();
                     assert Objects.requireNonNull(responseBody).size() == 3;
                 });
    }

    @Test
    void mono() {
        webTestClient
                .get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(integerFluxExchangeResult -> {
                    var responseBody = integerFluxExchangeResult.getResponseBody();
                    assertEquals("Hello World Flux And Mono", responseBody);
                });
    }

    @Test
    void stream() {
        var flux = webTestClient
                .get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier
                .create(flux)
                .expectNext(0L, 1L, 2L, 3L)
                .thenCancel()
                .verify();
    }

}