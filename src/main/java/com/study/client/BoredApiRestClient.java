package com.study.client;

import com.study.dto.ActivityDto;
import com.study.exceptionhandler.BoredApiClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class BoredApiRestClient {

    @Value("${boredApiBaseUrl}")
    private String baseUrl;
    private final String path = "/api/activity";

    private final WebClient webClient;

    public Mono<ActivityDto> retrieveTodo() {
        return webClient
                .get()
                .uri(baseUrl + path)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    var statusCode = clientResponse.statusCode().value();
                    log.info("Status code is : {}", statusCode);
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new BoredApiClientException(
                                "BoredApi not found",
                                statusCode));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage ->
                                    Mono.error(new BoredApiClientException(responseMessage, statusCode)));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    var statusCode = clientResponse.statusCode().value();
                    log.info("Status code is : {}", statusCode);

                    return Mono.error(new BoredApiClientException("Server Exception in BoredApi ", statusCode));
                })
                .bodyToMono(ActivityDto.class)
                .retry(3)
                .log();
    }

}
