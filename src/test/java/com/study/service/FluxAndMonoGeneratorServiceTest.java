package com.study.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var namesFlux = service.namesFlux();

        //then
        StepVerifier
                .create(namesFlux)
                .expectNext(
                        "Natalia",
                        "Lorraine",
                        "Rebeca",
                        "Cecilia",
                        "Gabriela",
                        "Ana",
                        "Jakeline",
                        "Karina",
                        "Caroline",
                        "Dayane",
                        "Erica",
                        "Raquel",
                        "Vanessa",
                        "Luana",
                        "Hellen",
                        "Gabriella",
                        "Rafaela"
                )
                .expectNextCount(0)  //verifies next events counter
                .verifyComplete();
    }

    @Test
    void namesUppercaseUsingMap() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlos"));
        //when
        var flux = service.namesUppercaseUsingMap(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("ALAN", "BENTO", "CARLOS")
                .verifyComplete();
    }

    @Test
    void namesHigherThanFourUsingFilter() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlao"));
        var size = 4;
        //when
        var flux = service.namesHigherThanSomeSizeUsingFilter(input, size);

        //then
        StepVerifier
                .create(flux)
                .expectNext("bento", "carlao")
                .verifyComplete();
    }

    @Test
    void namesFlatMap() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.charactersFromNames(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }

    @Test
    void namesFlatMapAsync() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.charactersFromNamesAsync(input);

        //then
        StepVerifier
                .create(flux)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesConcatMapAsync() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento"));

        //when
        var flux = service.charactersFromNamesAsyncWithConcatMap(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n","b","e","n","t","o")
                .verifyComplete();
    }

    @Test
    void charactersFromMonoFlatMap() {
        //given
        var input = Mono.just("alan");

        //when
        var flux = service.charactersFromName(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext(List.of("a","l","a","n"))
                .verifyComplete();
    }

    @Test
    void charactersFromMonoToFluxWithFlatMapMany() {
        //given
        var input = Mono.just("alan");

        //when
        var flux = service.charactersFromNameWithFlatMapMany(input);

        //then
        StepVerifier
                .create(flux)
                .expectNext("a","l","a","n")
                .verifyComplete();
    }

    @Test
    void transformFilteredNamesToUppercase() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlos"));


        //when
        var flux = service.transformNamesWithSizeHigherThan(input, 4);

        //then
        StepVerifier
                .create(flux)
                .expectNext("B", "E", "N", "T", "O", "C", "A", "R", "L", "O", "S")
                .verifyComplete();
    }

    @Test
    void defaultValueEmpty() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlos"));


        //when
        var flux = service.defaultNameIfEmptyWithSizeHigherThan(input, 999);

        //then
        StepVerifier
                .create(flux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void switchIfEmpty() {
        //given
        var input = Flux.fromIterable(List.of("alan", "bento", "carlos"));

        //when
        var flux = service.switchIfEmptyWithSizeHigherThan(input, 999);

        //then
        StepVerifier
                .create(flux)
                .expectNext("default", "value")
                .verifyComplete();
    }

    @Test
    void concatStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.concat(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatWithStreams() {
        //given
        var inputOne = Mono.just("C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.concatWith(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void mergeStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.merge(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeWithStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.mergeWith(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeSequentialStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.mergeSequential(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void zipStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.zipStreams(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zipWithStreams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");

        //when
        var flux = service.zipWithStreams(inputOne, inputTwo);

        //then
        StepVerifier
                .create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zip4Streams() {
        //given
        var inputOne = Flux.just("A", "B", "C");
        var inputTwo = Flux.just("D", "E", "F");
        var inputThree = Flux.just("1", "2", "3");
        var inputFour = Flux.just("4", "5", "6");

        //when
        var flux = service.zip4Streams(inputOne, inputTwo, inputThree, inputFour);

        //then
        StepVerifier
                .create(flux)
                .expectNext("1A4D", "2B5E", "3C6F")
                .verifyComplete();
    }
}
