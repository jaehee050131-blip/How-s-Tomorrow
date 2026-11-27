package com.oss.project.weather;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.IntFunction;

public class WeatherForecastConverter {

    public static WeatherForecast toForecast(
            City city,
            OpenMeteoResponse response,
            LocalDate targetDate,
            IntFunction<WeatherCondition> weatherMapper
    ) {
        Objects.requireNonNull(city, "City is required");
        Objects.requireNonNull(targetDate, "Date is required");
        Objects.requireNonNull(weatherMapper, "Weather mapper is required");

        if (response == null || response.daily() == null) {
            throw new IllegalArgumentException("Missing daily forecast");
        }

        OpenMeteoResponse.Daily daily = response.daily();

        if (daily.time() == null
                || daily.weatherCode() == null
                || daily.minTemperature() == null
                || daily.maxTemperature() == null) {
            throw new IllegalArgumentException("Incomplete forecast data");
        }

        int size = daily.time().size();

        if (daily.weatherCode().size() != size
                || daily.minTemperature().size() != size
                || daily.maxTemperature().size() != size) {
            throw new IllegalArgumentException("Inconsistent forecast data");
        }

        int index = daily.time().indexOf(targetDate.toString());

        if (index < 0) {
            throw new IllegalArgumentException(
                    "Forecast not found for: " + targetDate
            );
        }

        Integer weatherCode = daily.weatherCode().get(index);
        Double min = daily.minTemperature().get(index);
        Double max = daily.maxTemperature().get(index);

        if (weatherCode == null || min == null || max == null) {
            throw new IllegalArgumentException("Missing forecast values");
        }

        if (!Double.isFinite(min) || !Double.isFinite(max)) {
            throw new IllegalArgumentException("Invalid temperature");
        }

        if (min > max) {
            throw new IllegalArgumentException("Invalid temperature range");
        }

        int minTemp = Math.toIntExact(Math.round(min));
        int maxTemp = Math.toIntExact(Math.round(max));

        WeatherCondition condition =
                weatherMapper.apply(weatherCode);

        return new WeatherForecast(
                targetDate,
                city,
                condition,
                minTemp,
                maxTemp
        );
    }
}