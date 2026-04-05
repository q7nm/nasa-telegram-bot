package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NasaNeoFeedDTO(@JsonProperty("element_count") int elementCount,
                @JsonProperty("near_earth_objects") Map<String, List<AsteroidDTO>> nearEarthObjects) {
}