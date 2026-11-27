package com.oss.project.weather;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastConverterTest {

    private static final LocalDate DATE =
            LocalDate.of(2026, 11, 28);

    private OpenMeteoResponse createResponse(
            double min, double max
    ) {
        return new OpenMeteoResponse(
                new OpenMeteoResponse.Daily(
                        List.of("2026-11-27", "2026-11-28"),
                        List.of(0, 2),
                        List.of(6.1, min),
                        List.of(15.1, max)
                )
        );
    }

    private WeatherForecast convert(
            OpenMeteoResponse response
    ) {
        return WeatherForecastConverter.toForecast(
                City.SEOUL,
                response,
                DATE,
                code -> {
                    assertEquals(2, code);
                    return WeatherCondition.CLOUDY;
                }
        );
    }

    @Test
    void convertValidForecast() {
        WeatherForecast forecast =
                convert(createResponse(0.8, 8.7));

        assertEquals(DATE, forecast.date());
        assertEquals(City.SEOUL, forecast.city());
        assertEquals(WeatherCondition.CLOUDY,
                forecast.condition());
        assertEquals(1, forecast.minTemp());
        assertEquals(9, forecast.maxTemp());
    }

    @Test
    void equalTemperaturesAreValid() {
        WeatherForecast forecast =
                convert(createResponse(5.0, 5.0));

        assertEquals(5, forecast.minTemp());
        assertEquals(5, forecast.maxTemp());
    }

    @Test
    void equalTemperaturesAfterRoundingAreValid() {
        WeatherForecast forecast =
                convert(createResponse(5.1, 5.4));

        assertEquals(5, forecast.minTemp());
        assertEquals(5, forecast.maxTemp());
    }

    @Test
    void negativeTemperaturesAreValid() {
        WeatherForecast forecast =
                convert(createResponse(-8.7, -2.3));

        assertEquals(-9, forecast.minTemp());
        assertEquals(-2, forecast.maxTemp());
    }

    @Test
    void rejectInvalidTemperatureRange() {
        assertThrows(IllegalArgumentException.class,
                () -> convert(createResponse(15.0, 5.0)));
    }

    @Test
    void rejectMissingDailyData() {
        assertThrows(IllegalArgumentException.class,
                () -> convert(new OpenMeteoResponse(null)));
    }

    @Test
    void rejectMissingTargetDate() {
        assertThrows(IllegalArgumentException.class,
                () -> WeatherForecastConverter.toForecast(
                        City.SEOUL,
                        createResponse(0.8, 8.7),
                        DATE.plusDays(1),
                        code -> WeatherCondition.CLOUDY
                ));
    }

    @Test
    void rejectInconsistentDataLengths() {
        OpenMeteoResponse response =
                new OpenMeteoResponse(
                        new OpenMeteoResponse.Daily(
                                List.of("2026-11-28"),
                                List.of(0, 2),
                                List.of(0.8),
                                List.of(8.7)
                        )
                );

        assertThrows(IllegalArgumentException.class,
                () -> convert(response));
    }

    @Test
    void rejectNonFiniteTemperature() {
        assertThrows(IllegalArgumentException.class,
                () -> convert(
                        createResponse(Double.NaN, 8.7)
                ));
    }
}