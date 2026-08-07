package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MissDistanceDTO(@JsonProperty("lunar") String lunar, @JsonProperty("kilometers") String kilometers) {
}
