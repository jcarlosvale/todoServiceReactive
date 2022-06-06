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

    public Flux<String> namesUppercase() {
        return Flux.fromIterable(List.of("alan", "bento", "carlao")).map(s -> s.toUpperCase());
    }

    public Flux<String> namesUppercaseHigherThanFour() {
        return Flux
                .fromIterable(List.of("alan", "bento", "carlao"))
                .filter(s -> s.length() > 4)
                .map(s -> s.toUpperCase());
    }

    public Flux<String> namesFlatMap() {
        return Flux.fromIterable(List.of("alan", "bento"))
                .flatMap(s -> splitString(s))
                .log();
    }

    private Flux<String> splitString(String s) {
        return Flux.fromArray(s.split(""));
    }

    public Flux<String> namesFlatMapAssync() {
        return Flux.fromIterable(List.of("alan", "bento"))
                .flatMap(s -> splitStringWithDelay(s))
                .log();
    }

    private Flux<String> splitStringWithDelay(String s) {
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(s.split("")).delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> concatMap() {
        return Flux.fromIterable(List.of("alan", "bento"))
                .concatMap(s -> splitStringWithDelay(s))
                .log();
    }

    public Mono<List<String>> flatMapWithMono() {
        return Mono.just("java")
                .map(s -> s.toUpperCase())
                .flatMap(s -> splitStringMono(s));
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split("")));
    }

    public Flux<String> flatMapMany() {
        return Mono.just("java")
                .map(s -> s.toUpperCase())
                .flatMapMany(s -> splitString(s));
    }

    public Flux<String> transform() {
        Function<Flux<String>, Flux<String>> transformation =
                flux -> flux.map(s -> s.toUpperCase()).filter(s -> s.startsWith("A"));

        return Flux.fromIterable(List.of("alan", "bento"))
                .transform(transformation)
                .flatMap(s -> splitString(s))
                .log();
    }

    public Flux<String> ifEmpty() {
        return Flux.fromIterable(List.of("alan", "bento"))
                .filter(s -> s.length() > 6)
                .defaultIfEmpty("default value")
                .log();
    }

    public Flux<String> switchIfEmpty() {

        Flux<String> fluxDefault = Flux.just("Alan", "Bento");

        return Flux.fromIterable(List.of("alan", "bento"))
                .filter(s -> s.length() > 6)
                .switchIfEmpty(fluxDefault)
                .log();
    }

    public Flux<String> concat() {

        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> concatWith() {

        var aMono = Flux.just("A");
        var bMono = Flux.just("B");

        return aMono.concatWith(bMono).log();
    }

    public Flux<String> merge() {

        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> mergeWith() {

        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.mergeWith(bMono).log();
    }

    public Flux<String> mergeSequential() {

        var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> zip() {

        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.zip(abcFlux, defFlux, (a, b) -> a + b).log();
    }

    public Flux<String> otherZip() {

        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
                .map(tuple -> tuple.getT1() + tuple.getT2() + tuple.getT3() + tuple.getT4())
                .log();
    }

    public Flux<String> zipWith() {

        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.zipWith(defFlux, (a, b) -> a + b).log();
    }

    public Mono<String> zipWithMono() {

        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.zipWith(bMono).map(tuple -> tuple.getT1() + tuple.getT2()).log();
    }

    public static void main(String[] args) {
        var service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(System.out::println);

        service.namesMono()
                .subscribe(System.out::println);
    }
}
