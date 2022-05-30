package com.study.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromStream(
          Stream.of(
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
                  "Rafaela Santana Alves")).log();
    }

    public Mono<String> namesMono() {
        return Mono.just("Joao Carlos").log();
    }

    public Flux<String> namesUppercaseUsingMap(final Flux<String> names) {
        return null;
    }

    public Flux<String> namesHigherThanFourUsingFilter(final Flux<String> names) {
        return null;
    }

    public Flux<String> namesFlatMap(final Flux<String> names) {
        return null;
    }

    private Flux<String> splitString(String s) {
        return Flux.fromArray(s.split(""));
    }

    public Flux<String> namesFlatMapAssync(final Flux<String> names) {
        return names
                .flatMap(s -> splitStringWithDelay(s))
                .log();
    }

    private Flux<String> splitStringWithDelay(String s) {
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(s.split("")).delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> concatMap(final Flux<String> names) {
        return null;
    }

    public Mono<List<String>> flatMapWithMono(Mono<String> input) {
        return null;
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split("")));
    }

    public Flux<String> flatMapMany(Mono<String> input) {
        return null;
    }

    public Flux<String> transform(Flux<String> input) {
        return null;
    }

    public Flux<String> defaultIfEmpty(Flux<String> input) {
        return null;
    }

    public Flux<String> switchIfEmpty(Flux<String> input) {

        return null;
    }

    public Flux<String> concat(Flux<String> inputOneFlux, Flux<String> inputTwoFlux) {

        return null;
    }

    public Flux<String> concatWith(Flux<String> aMono, Flux<String> bMono) {

        return null;
    }

    public Flux<String> merge(Flux<String> abcFlux, Flux<String> defFlux) {

        return null;
    }

    public Flux<String> mergeWith(Mono<String> aMono, Mono<String> bMono) {

        return null;
    }

    public Flux<String> mergeSequential(Flux<String> abcFlux, Flux<String> defFlux) {

        return null;
    }

    public Flux<String> zip(Flux<String> abcFlux, Flux<String> defFlux) {

        return null;
    }

    public Flux<String> usingZipAgain(Flux<String> abcFlux, Flux<String> defFlux, Flux<String> _123Flux,
                                      Flux<String> _456Flux) {

        return null;
    }

    public Flux<String> zipWith(Flux<String> abcFlux, Flux<String> defFlux) {
        return null;
    }

    public Mono<String> zipWithMono(Mono<String> aMono, Mono<String> bMono) {

        return null;
    }

    public static void main(String[] args) {
        var service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(System.out::println);

        service.namesMono()
                .subscribe(System.out::println);
    }
}
