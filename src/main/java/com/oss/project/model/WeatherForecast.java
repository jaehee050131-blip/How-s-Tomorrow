package com.oss.project.model;

import java.time.LocalDate;
import java.util.Objects;

public record WeatherForecast(
        LocalDate date,
        City city,
        WeatherCondition condition,
        int minTemp,
        int maxTemp
) {

    public WeatherForecast {
        Objects.requireNonNull(date, "date is required");
        Objects.requireNonNull(city, "city is required");
        Objects.requireNonNull(condition, "condition is required");

        if (minTemp > maxTemp) {
            throw new IllegalArgumentException(
                    "Minimum temperature cannot exceed maximum temperature"
            );
        }
    }
}