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
    public Mono<String> mono() {
        return Mono.just("JAVA EH MUITO FIXE").log();
    }


    @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1,2,3,4,5).log();
    }

    @GetMapping(value="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream() {
        return Flux.interval(Duration.ofSeconds(1)).log();
        //return Flux.just(1L,2L,3L,4L,5L).delayElements(Duration.ofSeconds(1)).log();
    }


}
