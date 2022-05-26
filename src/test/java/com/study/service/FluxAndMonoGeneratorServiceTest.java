package com.study.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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

}