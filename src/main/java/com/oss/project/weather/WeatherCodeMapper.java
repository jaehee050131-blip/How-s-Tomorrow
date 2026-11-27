package com.oss.project.weather;

import com.oss.project.model.WeatherCondition;

public final class WeatherCodeMapper {

    private WeatherCodeMapper() {
    }

    // Open-Meteo daily weather codes (WMO) -> one of the four dragon brothers.
    // The rules were decided by the team in issue #17.
    public static WeatherCondition toCondition(int code) {
        return switch (code) {
            case 0, 1 -> WeatherCondition.SUNNY;
            case 2, 3, 45, 48 -> WeatherCondition.CLOUDY;
            case 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82, 95, 96, 99 -> WeatherCondition.RAINY;
            case 71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY;
            default -> throw new IllegalArgumentException("Unknown weather code: " + code);
        };
    }
}