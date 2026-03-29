package io.github.q7nm.nasa_telegram_bot.service.nasa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.q7nm.nasa_telegram_bot.client.NasaApiClient;
import io.github.q7nm.nasa_telegram_bot.entity.dto.ApodDTO;

@Service
public class NasaService {

    private final NasaApiClient nasaApiClient;

    @Autowired
    public NasaService(NasaApiClient nasaApiClient) {
        this.nasaApiClient = nasaApiClient;
    }

    @Cacheable("apod")
    public ApodDTO getApod() {
        return nasaApiClient.getApod().block();
    }

}
