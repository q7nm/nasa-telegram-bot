package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AsteroidDTO(String name, @JsonProperty("nasa_jpl_url") String nasaJplUrl,
        @JsonProperty("is_potentially_hazardous_asteroid") boolean potentiallyHazardous,
        @JsonProperty("estimated_diameter") DiameterDTO estimatedDiameter,
        @JsonProperty("close_approach_data") List<CloseApproachDTO> closeApproachData) {
}
