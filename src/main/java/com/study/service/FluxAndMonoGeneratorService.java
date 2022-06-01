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
        return names.map(String::toUpperCase);
    }

    public Flux<String> namesHigherThanFourUsingFilter(final Flux<String> names) {
        return names.filter(name -> name.length() > 4);
    }

    public Flux<String> namesFlatMap(final Flux<String> names) {
        return names.flatMap(name -> Flux.fromArray(name.split("")));
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
        return names.concatMap(name -> Flux.fromArray(name.split("")));
    }

    public Mono<List<String>> flatMapWithMono(Mono<String> input) {
        return input.flatMap(i -> Mono.just(List.of(i.toUpperCase().split(""))));
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split("")));
    }

    public Flux<String> flatMapMany(Mono<String> input) {
        return input.flatMapMany(i -> Flux.fromArray(i.toUpperCase().split("")));
    }

    public Flux<String> transform(Flux<String> input) {
        return
                input.filter(i -> i.equals("alan"))
                .map(i -> i.toUpperCase())
                .transform(flux -> flux.flatMap(f -> Flux.fromArray(f.split(""))));
    }

    public Flux<String> defaultIfEmpty(Flux<String> input) {
        return input.filter(i -> i.length() > 50).defaultIfEmpty("default value");
    }

    public Flux<String> switchIfEmpty(Flux<String> input) {
        return input.filter(i -> i.length() > 50)
                .switchIfEmpty(Flux.just("Alan", "Bento"));
    }

    public Flux<String> concat(Flux<String> inputOneFlux, Flux<String> inputTwoFlux) {

        return Flux.concat(inputOneFlux, inputTwoFlux);
    }

    public Flux<String> concatWith(Flux<String> aMono, Flux<String> bMono) {

        return aMono.concatWith(bMono);
    }

    public Flux<String> merge(Flux<String> abcFlux, Flux<String> defFlux) {

        return Flux.merge(abcFlux,defFlux);
    }

    public Flux<String> mergeWith(Mono<String> aMono, Mono<String> bMono) {

        return aMono.mergeWith(bMono);
    }

    public Flux<String> mergeSequential(Flux<String> abcFlux, Flux<String> defFlux) {

        return Flux.mergeSequential(abcFlux, defFlux);
    }

    public Flux<String> zip(Flux<String> abcFlux, Flux<String> defFlux) {

        return Flux.zip(abcFlux, defFlux, (elemento1, elemento2) -> elemento1 + elemento2);
    }

    public Flux<String> usingZipAgain(Flux<String> abcFlux, Flux<String> defFlux, Flux<String> _123Flux,
                                      Flux<String> _456Flux) {

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(tuple -> tuple.getT1() + tuple.getT2() + tuple.getT3() + tuple.getT4())
                .log();
    }

    public Flux<String> zipWith(Flux<String> abcFlux, Flux<String> defFlux) {
        return abcFlux.zipWith(defFlux, (elementoFlux1, elementoFlux2) -> elementoFlux1 + elementoFlux2);
    }

    public Mono<String> zipWithMono(Mono<String> aMono, Mono<String> bMono) {
        return aMono.zipWith(bMono, (mono1, mono2) -> mono1 + mono2);
    }

    public static void main(String[] args) {
        var service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(System.out::println);

        service.namesMono()
                .subscribe(System.out::println);
    }
}
