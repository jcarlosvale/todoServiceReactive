package com.study.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
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
        return Flux.fromIterable(List.of("alan", "bento", "carlao"));
    }

    public Flux<String> namesUppercaseHigherThanFour() {
        return Flux.fromIterable(List.of("alan", "bento", "carlao"));
    }

    public static void main(String[] args) {
        var service = new FluxAndMonoGeneratorService();

        service.namesFlux()
                .subscribe(System.out::println);

        service.namesMono()
                .subscribe(System.out::println);
    }
}
