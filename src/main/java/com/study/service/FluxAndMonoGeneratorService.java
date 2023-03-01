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
        return null;
    }

    public Flux<String> namesHigherThanSomeSizeUsingFilter(Flux<String> input, int size) {
        return null;
    }

    public Flux<String> charactersFromNames(Flux<String> input) {
        return null;
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
        return null;
    }

    public Mono<List<String>> charactersFromName(Mono<String> input) {
        return null;
    }

    public Flux<String> charactersFromNameWithFlatMapMany(Mono<String> input) {
        return null;
    }

    public Flux<String> transformNamesWithSizeHigherThan(Flux<String> input, int size) {
        //FIXME: implemente a função abaixo
        Function<Flux<String>, Flux<String>> function = null;
        return input
                .transform(function)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .log();
    }

    public Flux<String> defaultNameIfEmptyWithSizeHigherThan(Flux<String> input, int size) {
        return null;
    }

    public Flux<String> switchIfEmptyWithSizeHigherThan(Flux<String> input, int size) {
        return null;
    }

    public Flux<String> concat(Flux<String> inputOne, Flux<String> inputTwo) {
        return null;
    }

    public Flux<String> concatWith(Mono<String> inputOne, Flux<String> inputTwo) {
        return null;
    }

    public Flux<String> merge(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        //FIXME merge fluxA e fluxB
        return null;
    }

    public Flux<String> mergeWith(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        //FIXME merge fluxA e fluxB
        return null;
    }

    public Flux<String> mergeSequential(Flux<String> inputOne, Flux<String> inputTwo) {
        var fluxA = inputOne.delayElements(Duration.ofMillis(100));
        var fluxB = inputTwo.delayElements(Duration.ofMillis(125));

        //FIXME merge fluxA e fluxB
        return null;
    }

    public Flux<String> zipStreams(Flux<String> inputOne, Flux<String> inputTwo) {
        return null;
    }

    public Flux<String> zipWithStreams(Flux<String> inputOne, Flux<String> inputTwo) {
        return null;
    }

    public Flux<String> zip4Streams(Flux<String> inputOne, Flux<String> inputTwo, Flux<String> inputThree, Flux<String> inputFour) {
        return Flux
                .zip(inputOne, inputTwo, inputThree, inputFour)
                .map(t4 -> t4.getT3() + t4.getT1() + t4.getT4() + t4.getT2())
                .log();
    }
}
