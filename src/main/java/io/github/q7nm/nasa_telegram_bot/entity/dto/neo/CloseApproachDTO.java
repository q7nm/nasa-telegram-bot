package io.github.q7nm.nasa_telegram_bot.entity.dto.neo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CloseApproachDTO(@JsonProperty("close_approach_date") String closeApproachDate,
                @JsonProperty("close_approach_date_full") String closeApproachDateFull,
                @JsonProperty("epoch_date_close_approach") Long epochDateCloseApproach,
                @JsonProperty("relative_velocity") RelativeVelocityDTO relativeVelocity,
                @JsonProperty("miss_distance") MissDistanceDTO missDistance,
                @JsonProperty("orbiting_body") String orbitingBody) {
}
