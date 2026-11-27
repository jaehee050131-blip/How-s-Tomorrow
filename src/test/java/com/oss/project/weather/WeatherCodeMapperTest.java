package com.oss.project.weather;

import com.oss.project.model.WeatherCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherCodeMapperTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void clearSkyIsSunny(int code) {
        assertEquals(WeatherCondition.SUNNY, WeatherCodeMapper.toCondition(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 45, 48})
    void cloudsAndFogAreCloudy(int code) {
        assertEquals(WeatherCondition.CLOUDY, WeatherCodeMapper.toCondition(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {51, 53, 55, 61, 63, 65, 80, 81, 82, 95, 96, 99})
    void drizzleRainShowersAndThunderstormsAreRainy(int code) {
        assertEquals(WeatherCondition.RAINY, WeatherCodeMapper.toCondition(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {56, 57, 66, 67})
    void freezingDrizzleAndFreezingRainAreRainy(int code) {
        assertEquals(WeatherCondition.RAINY, WeatherCodeMapper.toCondition(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {71, 73, 75, 77, 85, 86})
    void snowAndSnowShowersAreSnowy(int code) {
        assertEquals(WeatherCondition.SNOWY, WeatherCodeMapper.toCondition(code));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 10, 100, -1})
    void unknownCodeIsInvalid(int code) {
        assertThrows(IllegalArgumentException.class, () -> WeatherCodeMapper.toCondition(code));
    }
}