package com.study.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/mono")
    public Mono<String> mono(){
        return Mono.just("Viva o Java").log();
    }

    @GetMapping("/flux")
    public Flux<Integer> flux(){
        return Flux.just(1,2,3,4,5).log();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream(){
        return Flux.interval(Duration.ofSeconds(1));
    }
}
