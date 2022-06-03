package com.study;

import com.study.model.UserDocument;
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
class TodoServiceReactiveApplicationTests {

	@Autowired
	private UserRepository repository;

	@Autowired
	private WebTestClient webTestClient;

	private static final String URL = "/users";

	@BeforeEach
	void setup() {
		var documents =
				List.of(
						UserDocument.builder().username("jose").name("jose paulo").todos(List.of("Correr", "Estudar")).build(),
						UserDocument.builder().username("maria").name("maria luiza").todos(List.of("Acordar", "Estudar")).build(),
						UserDocument.builder().username("joao").name("joao mario").todos(List.of("Brincar", "Dormir", "Viajar")).build()
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
		var newDocument = UserDocument.builder().username("ze").name("ze paulo").todos(List.of("Correr", "Estudar")).build();

		//WHEN  //THEN
		webTestClient
				.post()
				.uri(URL)
				.bodyValue(newDocument)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(UserDocument.class)
				.consumeWith(result -> {
					var actual = result.getResponseBody();
					assert actual != null;
					assertThat(actual.getUsername()).isEqualTo("ze");
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
				.expectBodyList(UserDocument.class)
				.hasSize(3);
	}

	@Test
	void getById() {
		//GIVEN
		String id = "maria";
		var expected = UserDocument.builder().username("maria").name("maria luiza").todos(List.of("Acordar", "Estudar")).build();

		//WHEN  //THEN
		webTestClient
				.get()
				.uri(URL + "/{id}", id)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(UserDocument.class)
				.consumeWith(result -> {
					var document = result.getResponseBody();
					assertThat(document).isEqualTo(expected);
				});
	}

	@Test
	void getByIdNotFound() {
		//GIVEN
		String id = "def";

		//WHEN  //THEN
		webTestClient
				.get()
				.uri(URL + "/{id}", id)
				.exchange()
				.expectStatus()
				.isNotFound();
	}

	@Test
	void update() {
		//GIVEN
		var id = "jose";
		var updated = UserDocument.builder().name("ze paulo").todos(List.of("Correr")).build();
		var expected = UserDocument.builder().username("jose").name("ze paulo").todos(List.of("Correr")).build();

		//WHEN  //THEN
		webTestClient
				.put()
				.uri(URL + "/{id}", id)
				.bodyValue(updated)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(UserDocument.class)
				.consumeWith(result -> {
					var actual = result.getResponseBody();
					assert actual != null;
					assertThat(actual).isEqualTo(expected);
				});
	}

	@Test
	void updateNotFound() {
		//GIVEN
		var id = "xyz";
		var updated = UserDocument.builder().name("ze paulo").todos(List.of("Correr")).build();

		//WHEN  //THEN
		webTestClient
				.put()
				.uri(URL + "/{id}", id)
				.bodyValue(updated)
				.exchange()
				.expectStatus()
				.isNotFound();
	}

	@Test
	void delete() {
		//GIVEN
		var id = "jose";

		//WHEN  //THEN
		webTestClient
				.delete()
				.uri(URL + "/{id}", id)
				.exchange()
				.expectStatus()
				.isNoContent()
				.expectBody(Void.class);
	}

}

