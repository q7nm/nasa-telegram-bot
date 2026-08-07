package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RelativeVelocityDTO(@JsonProperty("kilometers_per_hour") String kilometersPerHour) {
}
