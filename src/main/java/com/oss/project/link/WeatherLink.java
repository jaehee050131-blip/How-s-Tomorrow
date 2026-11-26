package com.oss.project.link;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public final class WeatherLink {

    private WeatherLink() {
    }

    public static String create(WeatherForecast forecast) {
        return "/view"
                + "?city=" + forecast.city().getCode()
                + "&date=" + forecast.date()
                + "&weather=" + forecast.condition().getCode()
                + "&min=" + forecast.minTemp()
                + "&max=" + forecast.maxTemp();
    }

    public static WeatherForecast read(Map<String, String> values) {
        City city = City.fromCode(required(values, "city"));
        LocalDate date = parseDate(required(values, "date"));
        WeatherCondition condition = WeatherCondition.fromCode(required(values, "weather"));
        int minTemp = Integer.parseInt(required(values, "min"));
        int maxTemp = Integer.parseInt(required(values, "max"));
        return new WeatherForecast(date, city, condition, minTemp, maxTemp);
    }

    private static String required(Map<String, String> values, String name) {
        String value = values.get(name);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing link value: " + name);
        }
        return value;
    }

    private static LocalDate parseDate(String text) {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date in link: " + text, e);
        }
    }
}