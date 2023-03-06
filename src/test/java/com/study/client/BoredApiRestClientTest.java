package com.study.client;

import com.study.domain.TodoInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")  //new versions of Mongo broken the Embedded
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 8084)
@TestPropertySource(
        properties = {
                "boredApiBaseUrl=http://localhost:8084",
                "spring.mongodb.embedded.version=3.5.5"
        }
)
class BoredApiRestClientTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void retrieveActivity() {
        //given
        stubFor(
                get("/api/activity")
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{\n" +
                                                "  \"activity\": \"Watch the sunset or the sunrise\",\n" +
                                                "  \"type\": \"recreational\",\n" +
                                                "  \"participants\": 1,\n" +
                                                "  \"price\": 0,\n" +
                                                "  \"link\": \"\",\n" +
                                                "  \"key\": \"4748214\",\n" +
                                                "  \"accessibility\": 1\n" +
                                                "}")));

        //when
        webTestClient
                .get()
                .uri("/v1/todoinfos/activity")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TodoInfo.class)
                .consumeWith(todoInfoEntityExchangeResult -> {
                    var todoInfo = todoInfoEntityExchangeResult.getResponseBody();
                    assert todoInfo != null;
                    Assertions.assertEquals("Watch the sunset or the sunrise", todoInfo.getDescription());
                });
    }

    @Test
    void retrieveActivityNotFound() {
        //given
        stubFor(
                get("/api/activity")
                        .willReturn(
                                aResponse()
                                        .withStatus(404)));

        //when
        webTestClient
                .get()
                .uri("/v1/todoinfos/activity")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

}