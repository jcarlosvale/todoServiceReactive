package com.study.repository;

import com.study.domain.TodoInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")  //new versions of Mongo broken the Embedded
class TodoInfoRepositoryTest {

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
    void findAll() {
        //given

        //when
        var todosInfo = todoInfoRepository.findAll().log();

        //then
        StepVerifier.create(todosInfo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findByDate() {
        //given
        var date = LocalDate.parse("2020-06-11");

        //when
        var todosInfo = todoInfoRepository.findByTodoDate(date);

        //then
        StepVerifier.create(todosInfo)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findById() {
        //given

        //when
        var todoInfoMono = todoInfoRepository.findById("abc").log();

        //then
        StepVerifier.create(todoInfoMono)
                .assertNext(todoInfo -> {
                    assertEquals("Wash my car", todoInfo.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void save() {
        //given
        var newTodo = new TodoInfo(null, "Homework 1", LocalDate.parse("2008-07-18"));

        //when
        var todoInfo = todoInfoRepository.save(newTodo).log();

        //then
        StepVerifier.create(todoInfo)
                .assertNext(todo -> {
                    assertNotNull(todo.getTodoInfoId());
                    assertEquals("Homework 1", todo.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void update() {
        //given
        var todoInfoMono = todoInfoRepository.findById("abc").block();
        assert todoInfoMono != null;
        todoInfoMono.setTodoDate(LocalDate.parse("2023-07-18"));

        //when
        var todoInfoUpdated = todoInfoRepository.save(todoInfoMono).log();

        //then
        StepVerifier.create(todoInfoUpdated)
                .assertNext(todo -> {
                    assertEquals(LocalDate.parse("2023-07-18"), todo.getTodoDate());
                })
                .verifyComplete();
    }

    @Test
    void delete() {
        //given

        //when
        todoInfoRepository.deleteById("abc").block();
        var todosInfoFlux = todoInfoRepository.findAll().log();

        //then
        StepVerifier.create(todosInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}