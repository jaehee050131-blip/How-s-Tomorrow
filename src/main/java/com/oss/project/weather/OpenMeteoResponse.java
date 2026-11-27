package com.oss.project.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenMeteoResponse(
        Daily daily
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Daily(
            List<String> time,

            @JsonProperty("weather_code")
            List<Integer> weatherCode,

            @JsonProperty("temperature_2m_min")
            List<Double> minTemperature,

            @JsonProperty("temperature_2m_max")
            List<Double> maxTemperature
    ) {
    }
}