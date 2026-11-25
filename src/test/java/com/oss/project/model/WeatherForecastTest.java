package com.oss.project.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastTest {

    @Test
    void validCityCode() {
        assertEquals(City.SEOUL, City.fromCode("seoul"));
        assertEquals("서울", City.SEOUL.getKoreanName());
    }

    @Test
    void invalidCityCode() {
        assertThrows(IllegalArgumentException.class,
                () -> City.fromCode("tokyo"));
    }

    @Test
    void validWeatherCode() {
        assertEquals(WeatherCondition.RAINY,
                WeatherCondition.fromCode("rainy"));
    }

    @Test
    void invalidWeatherCode() {
        assertThrows(IllegalArgumentException.class,
                () -> WeatherCondition.fromCode("unknown"));
    }

    @Test
    void createValidForecast() {
        LocalDate date = LocalDate.of(2026, 11, 26);

        WeatherForecast forecast = new WeatherForecast(
                date,
                City.SEOUL,
                WeatherCondition.RAINY,
                3,
                9
        );

        assertEquals(date, forecast.date());
        assertEquals(City.SEOUL, forecast.city());
        assertEquals(WeatherCondition.RAINY, forecast.condition());
        assertEquals(3, forecast.minTemp());
        assertEquals(9, forecast.maxTemp());
    }

    @Test
    void invalidTemperatureRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new WeatherForecast(
                        LocalDate.of(2026, 11, 26),
                        City.SEOUL,
                        WeatherCondition.SUNNY,
                        15,
                        5
                ));
    }
}