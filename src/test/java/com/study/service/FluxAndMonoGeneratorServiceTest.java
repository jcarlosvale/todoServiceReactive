package com.study.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void namesHigherThanFourUsingFilter() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlao"));

        //when
        var flux = service.namesHigherThanFourUsingFilter(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("bento", "carlao")
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
    void namesUppercaseUsingMap() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlao"));
        //when
        var flux = service.namesUppercaseUsingMap(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("ALAN", "BENTO", "CARLAO")
                .verifyComplete();
    }

    @Test
    void namesFlatMap() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.namesFlatMap(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }

    @Test
    void namesFlatMapWithDelay() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.namesFlatMapAssync(input);

        //then
        StepVerifier
                .create(flux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void concatMap() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.concatMap(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }


    @Test
    void flatMapWithMono() {
        //given
        var input = Mono.just("java");

        //when
        var mono = service.flatMapWithMono(input);

        //then
        StepVerifier
                .create(mono)
                .expectNext(List.of("J","A","V","A"))
                .verifyComplete();
    }

    @Test
    void flatMapMany() {
        //given
        var input = Mono.just("java");

        //when
        var flux = service.flatMapMany(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("J","A","V","A")
                .verifyComplete();
    }

    @Test
    void transform() {
        //given
        var input = Flux.just("alan", "bento");

        //when
        var flux = service.transform(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A","L", "A", "N")
                .verifyComplete();
    }

    @Test
    void ifEmpty() {
        //given
        var input = Flux.just("alan", "bento");

        //when
        var flux = service.defaultIfEmpty(input); //crie algum filtro que nao retorna nada

        //then
        StepVerifier
                .create(flux)
                .expectNext("default value")
                .verifyComplete();
    }

    @Test
    void switchIfEmpty() {
        //given
        var input = Flux.just("some", "string");

        //when
        var flux = service.switchIfEmpty(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("Alan", "Bento")
                .verifyComplete();
    }

    @Test
    void concat() {
        //given
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        //when
        var flux = service.concat(abcFlux, defFlux);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatWith() {
        //given
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        //when
        var flux = service.concatWith(aMono, bMono);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void merge() {
        //given
        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        //when
        var flux = service.merge(abcFlux, defFlux).log();

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeWith() {
        //given
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        //when
        var flux = service.mergeWith(aMono, bMono);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void mergeSequential() {
        //given
        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        //when
        var flux = service.mergeSequential(abcFlux, defFlux);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void zip() {
        //given
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        //when
        var flux = service.zip(abcFlux, defFlux);

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void otherZip() {
        //given
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");

        //when
        var flux = service.usingZipAgain(abcFlux, defFlux, _123Flux, _456Flux);

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void zipWith() {
        //given
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        //when
        var flux = service.zipWith(abcFlux, defFlux);

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zipWithMono() {
        //given
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        //when
        var mono = service.zipWithMono(aMono, bMono);

        //then
        StepVerifier
                .create(mono)
                .expectNext("AB")
                .verifyComplete();
    }

    @Test
    void sample() {
        var lista = Stream.of("alan", "bento")
                .flatMap(string -> Stream.of(string.split("")))
                .collect(Collectors.toList());

        System.out.println(lista);
    }

}