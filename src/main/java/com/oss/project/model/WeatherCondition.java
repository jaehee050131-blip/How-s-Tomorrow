package com.oss.project.model;

public enum WeatherCondition {

    SUNNY("sunny"),
    CLOUDY("cloudy"),
    RAINY("rainy"),
    SNOWY("snowy");

    private final String code;

    WeatherCondition(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static WeatherCondition fromCode(String code) {
        for (WeatherCondition condition : values()) {
            if (condition.code.equals(code)) {
                return condition;
            }
        }

        throw new IllegalArgumentException(
                "Unknown weather code: " + code
        );
    }
}