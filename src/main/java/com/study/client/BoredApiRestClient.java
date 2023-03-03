package com.study.client;

import com.study.dto.ActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BoredApiRestClient {

    private final String url = "https://www.boredapi.com/api/activity";

    private final WebClient webClient;

    public Mono<ActivityDto> retrieveTodo() {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ActivityDto.class)
                .log();
    }

}
