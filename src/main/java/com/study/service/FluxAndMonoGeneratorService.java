package com.study.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                        "Rafaela"));
    }

    public Mono<String> namesMono() {
        return Mono.just("Joao Carlos");
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
}
