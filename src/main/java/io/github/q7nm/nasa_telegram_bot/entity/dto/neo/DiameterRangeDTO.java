package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiameterRangeDTO(@JsonProperty("estimated_diameter_min") double min,
        @JsonProperty("estimated_diameter_max") double max) {
}
