package io.github.q7nm.nasa_telegram_bot.client;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.q7nm.nasa_telegram_bot.entity.dto.ApodDTO;
import io.github.q7nm.nasa_telegram_bot.entity.dto.neo.NasaNeoFeedDTO;
import reactor.core.publisher.Mono;

@Component
public class NasaApiClient {

    private final WebClient webClient;
    private final String apiKey;

    @Autowired
    public NasaApiClient(@Value("${nasa.api.key}") String apiKey, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.nasa.gov").build();
        this.apiKey = apiKey;
    }

    public Mono<ApodDTO> getApod() {
        return webClient.get().uri(uriBuilder -> uriBuilder.path("/planetary/apod").queryParam("api_key", apiKey).build())
                .retrieve().bodyToMono(ApodDTO.class);
    }

    public Mono<NasaNeoFeedDTO> getNeoFeed(LocalDate startDate, LocalDate endDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/neo/rest/v1/feed").queryParam("start_date", startDate)
                        .queryParam("end_date", endDate).queryParam("api_key", apiKey).build())
                .retrieve().bodyToMono(NasaNeoFeedDTO.class);
    }

}
