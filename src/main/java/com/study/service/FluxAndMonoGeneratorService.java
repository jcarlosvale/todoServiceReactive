package com.study.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromStream(
                Stream.of(
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
                        "Rafaela"))
                .log();
    }

    public Mono<String> namesMono() {
        return Mono.just("Joao Carlos").log();
    }

    public static void main(String[] args) {
        var service = new FluxAndMonoGeneratorService();

        System.out.println("\n\n\nFLUX");
        service.namesFlux()
                .subscribe(System.out::println);

        System.out.println("\n\n\nMONO");
        service.namesMono()
                .subscribe(System.out::println);

        System.out.println("\n\n\n");
    }

    public Flux<String> namesUppercaseUsingMap(Flux<String> input) {
        return input
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesHigherThanSomeSizeUsingFilter(Flux<String> input, int size) {
        return input
                .filter(s -> s.length() > size)
                .log();
    }

    public Flux<String> charactersFromNames(Flux<String> input) {
        return input
                .flatMap(s -> Flux.fromArray(s.split("")))
                .log();
    }

    public Flux<String> charactersFromNamesAsync(Flux<String> input) {
        return input
                .flatMap(this::splitWithSomeDelay)
                .doOnNext(System.out::println)
                .log();
    }

    private Flux<String> splitWithSomeDelay(String s) {
        var delay = new Random().nextInt(1000);
        System.out.println("Delay: " + delay);
        return Flux.fromArray(s.split(""))
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> charactersFromNamesAsyncWithConcatMap(Flux<String> input) {
        return input
                .concatMap(this::splitWithSomeDelay)
                .doOnNext(System.out::println)
                .log();
    }

    public Mono<List<String>> charactersFromName(Mono<String> input) {
        return input
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> charactersFromNameWithFlatMapMany(Mono<String> input) {
        return input
                .flatMapMany(s -> Flux.fromArray(s.split("")))
                .log();
    }

    public Flux<String> transformNamesWithSizeHigherThan(Flux<String> input, int size) {
        Function<Flux<String>, Flux<String>> function =
                stringFlux ->
                        stringFlux
                                .filter(s -> s.length() > size)
                                .map(String::toUpperCase);
        return input
                .transform(function)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .log();
    }

    public Flux<String> defaultNameIfEmptyWithSizeHigherThan(Flux<String> input, int size) {
        return
                input
                        .filter(s -> s.length() > size)
                        .defaultIfEmpty("default")
                        .log();
    }

    public Flux<String> switchIfEmptyWithSizeHigherThan(Flux<String> input, int size) {
        return
                input
                        .filter(s -> s.length() > size)
                        .switchIfEmpty(Flux.just("default", "value"))
                        .log();
    }

    public Flux<String> concat(Flux<String> inputOne, Flux<String> inputTwo) {
        return Flux
                .concat(inputOne, inputTwo)
                .log();
    }

    public Flux<String> concatWith(Mono<String> inputOne, Flux<String> inputTwo) {
        return inputOne
                .concatWith(inputTwo)
                .log();
    }

    public Flux<String> merge(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        return Flux.merge(fluxA, fluxB).log();
    }

    public Flux<String> mergeWith(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        return fluxA.mergeWith(fluxB).log();
    }

    public Flux<String> mergeSequential(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(fluxA, fluxB).log();
    }

    public Flux<String> zipStreams(Flux<String> inputOne, Flux<String> inputTwo) {
        return Flux
                .zip(inputOne, inputTwo, (s, s2) -> s + s2)
                .log();
    }

    public Flux<String> zipWithStreams(Flux<String> inputOne, Flux<String> inputTwo) {
        return inputOne
                .zipWith(inputTwo, (s, s2) -> s + s2)
                .log();
    }

    public Flux<String> zip4Streams(Flux<String> inputOne, Flux<String> inputTwo, Flux<String> inputThree, Flux<String> inputFour) {
        return Flux
                .zip(inputOne, inputTwo, inputThree, inputFour)
                .map(t4 -> t4.getT3() + t4.getT1() + t4.getT4() + t4.getT2())
                .log();
    }
}
