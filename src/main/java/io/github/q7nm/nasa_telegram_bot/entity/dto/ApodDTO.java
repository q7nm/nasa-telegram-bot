package io.github.q7nm.nasa_telegram_bot.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApodDTO(String title, String explanation, String url, String copyright,
        @JsonProperty("media_type") String mediaType) {
}