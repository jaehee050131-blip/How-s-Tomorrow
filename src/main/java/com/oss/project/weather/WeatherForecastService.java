package com.oss.project.weather;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.function.IntFunction;

@Service
public class WeatherForecastService {

    private static final ZoneId KOREA_ZONE =
            ZoneId.of("Asia/Seoul");

    private final OpenMeteoClient client;
    private final Clock clock;

    @Autowired
    public WeatherForecastService(OpenMeteoClient client) {
        this(client, Clock.systemUTC());
    }

    WeatherForecastService(
            OpenMeteoClient client,
            Clock clock
    ) {
        this.client = Objects.requireNonNull(client);
        this.clock = Objects.requireNonNull(clock);
    }

    public WeatherForecast fetchTomorrow(
            City city,
            IntFunction<WeatherCondition> weatherMapper
    ) {
        Objects.requireNonNull(city, "City is required");
        Objects.requireNonNull(weatherMapper, "Weather mapper is required");

        LocalDate tomorrow = LocalDate
                .now(clock.withZone(KOREA_ZONE))
                .plusDays(1);

        OpenMeteoResponse response =
                client.fetchForecast(city);

        return WeatherForecastConverter.toForecast(
                city,
                response,
                tomorrow,
                weatherMapper
        );
    }
}