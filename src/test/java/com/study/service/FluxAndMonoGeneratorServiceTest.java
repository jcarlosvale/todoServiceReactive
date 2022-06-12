package com.study.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    String [] arrayOfNames = {
            "Natalia Regina dos Santos Rocha",
            "Lorraine Letiele Pires",
            "Rebeca Baptista Fonseca Viana",
            "Cecilia Andrea Pesce",
            "Gabriela dos Santos Silva Paes",
            "Ana Carolina Toledo Lobo Maia",
            "Jakeline Santana da Rocha",
            "Karina Piloupas da Costa",
            "Caroline de Souza Cagnin",
            "Dayane Sousa Baia",
            "Erica Ferreira Cruz Nunes",
            "Raquel Souza da Silva",
            "Vanessa Correa de Oliveira Kodama",
            "Luana Barbosa Bento Ferreira",
            "Hellen Caldas Rios",
            "Gabriella Lemos da Silva Vaz",
            "Rafaela Santana Alves"};

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var flux = service.namesFlux();

        //then
        StepVerifier
                .create(flux)
                .expectNext(arrayOfNames)
                .verifyComplete();
    }

    @Test
    void namesFluxCount() {
        //given

        //when
        var flux = service.namesFlux();

        //then
        StepVerifier
                .create(flux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void namesFluxMixed() {
        //given

        //when
        var flux = service.namesFlux();

        //then
        StepVerifier
                .create(flux)
                .expectNext("Natalia Regina dos Santos Rocha", "Lorraine Letiele Pires")
                .expectNextCount(15)
                .verifyComplete();
    }

    @Test
    void namesUppercase() {
        //given

        //when
        var flux = service.namesUppercase();

        //then
        StepVerifier
                .create(flux)
                .expectNext("ALAN", "BENTO", "CARLAO")
                .verifyComplete();
    }

    @Test
    void namesUppercaseHigherThanFour() {
        //given

        //when
        var flux = service.namesUppercaseHigherThanFour();

        //then
        StepVerifier
                .create(flux)
                .expectNext("BENTO", "CARLAO")
                .verifyComplete();
    }

    @Test
    void namesFlatMap() {
        //given
        //when
        var flux = service.namesFlatMap();

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }

    @Test
    void namesFlatMapWithDelay() {
        //given
        //when
        var flux = service.namesFlatMapAssync();

        //then
        StepVerifier
                .create(flux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void concatMap() {
        //given
        //when
        var flux = service.concatMap();

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }


    @Test
    void flatMapWithMono() {
        //given
        //when
        var mono = service.flatMapWithMono();

        //then
        StepVerifier
                .create(mono)
                .expectNext(List.of("J","A","V","A"))
                .verifyComplete();
    }

    @Test
    void flatMapMany() {
        //given
        //when
        var flux = service.flatMapMany();

        //then
        StepVerifier
                .create(flux)
                .expectNext("J","A","V","A")
                .verifyComplete();
    }

    @Test
    void transform() {
        //given
        //when
        var flux = service.transform();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A","L", "A", "N")
                .verifyComplete();
    }

    @Test
    void ifEmpty() {
        //given
        //when
        var flux = service.ifEmpty();

        //then
        StepVerifier
                .create(flux)
                .expectNext("default value")
                .verifyComplete();
    }

    @Test
    void switchIfEmpty() {
        //given
        //when
        var flux = service.switchIfEmpty();

        //then
        StepVerifier
                .create(flux)
                .expectNext("Alan", "Bento")
                .verifyComplete();
    }

    @Test
    void concat() {
        //given
        //when
        var flux = service.concat();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatWith() {
        //given
        //when
        var flux = service.concatWith();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void merge() {
        //given
        //when
        var flux = service.merge();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeWith() {
        //given
        //when
        var flux = service.mergeWith();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void mergeSequential() {
        //given
        //when
        var flux = service.mergeSequential();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void zip() {
        //given
        //when
        var flux = service.zip();

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void otherZip() {
        //given
        //when
        var flux = service.otherZip();

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void zipWith() {
        //given
        //when
        var flux = service.zipWith();

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zipWithMono() {
        //given
        //when
        var mono = service.zipWithMono();

        //then
        StepVerifier
                .create(mono)
                .expectNext("AB")
                .verifyComplete();
    }
}