package com.study.controller;

import com.study.model.ProductDocument;
import com.study.model.repository.ProductRepository;
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
class ProductControllerTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    private static final String URL = "/v1/products";



    @BeforeEach
    void setup() {
        var documents =
                List.of(
                        ProductDocument.builder().name("produto 1").price(1.50).description("description 1").build(),
                        ProductDocument.builder().name("produto 2").price(3.50).description("description 2").build(),
                        ProductDocument.builder().name("produto 3").price(2.55).description("description 3").build()
                );

        repository.saveAll(documents).blockLast();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
    }

    @Test
    void save() {
        //GIVEN
        var newDocument = ProductDocument.builder().name("produto 4").price(0.50).description("description 4").build();

        //WHEN  //THEN
        webTestClient
                .post()
                .uri(URL)
                .bodyValue(newDocument)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ProductDocument.class)
                .consumeWith(result -> {
                    var actual = result.getResponseBody();
                    assert actual != null;
                    assertThat(actual.getName()).isEqualTo("produto 4");
                });
    }

    @Test
    void getAll() {
        //GIVEN

        //WHEN  //THEN
        webTestClient
                .get()
                .uri(URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(ProductDocument.class)
                .hasSize(3);
    }

    @Test
    void getByName() {
        //GIVEN
        String name = "produto 2";
        var expected = ProductDocument.builder()
                .name("produto 2")
                .price(3.50)
                .description("description 2").build();

        //WHEN  //THEN
        webTestClient
                .get()
                .uri(URL + "/{name}", name)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ProductDocument.class)
                .consumeWith(result -> {
                    var document = result.getResponseBody();
                    assertThat(document).isEqualTo(expected);
                });
    }

    @Test
    void getByNameNotFound() {
        //GIVEN
        String name = "def";

        //WHEN  //THEN
        webTestClient
                .get()
                .uri(URL + "/{name}", name)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void update() {
        //GIVEN
        var name = "produto 3";
        var expected = ProductDocument.builder().name("produto 3")
                .price(10.50).description("description 3").build();

        //WHEN  //THEN
        webTestClient
                .put()
                .uri(URL + "/{name}", name)
                .bodyValue(expected)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ProductDocument.class)
                .consumeWith(result -> {
                    var actual = result.getResponseBody();
                    assert actual != null;
                    assertThat(actual).isEqualTo(expected);
                });
    }

    @Test
    void delete() {
        //GIVEN
        var name = "produto 3";

        //WHEN  //THEN
        webTestClient
                .delete()
                .uri(URL + "/{name}", name)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
    }


}