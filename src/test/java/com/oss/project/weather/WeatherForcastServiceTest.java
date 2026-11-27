package com.oss.project.weather;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class WeatherForecastServiceTest {

    private void verifyTomorrowForecast(
            String utcTime,
            LocalDate expectedTomorrow
    ) {
        Clock clock = Clock.fixed(
                Instant.parse(utcTime),
                ZoneOffset.UTC
        );

        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://api.open-meteo.com");

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        OpenMeteoClient client =
                new OpenMeteoClient(builder.build());

        WeatherForecastService service =
                new WeatherForecastService(client, clock);

        String json = """
                {
                  "daily": {
                    "time": ["%s", "%s"],
                    "weather_code": [0, 2],
                    "temperature_2m_min": [6.1, 0.8],
                    "temperature_2m_max": [15.1, 8.7]
                  }
                }
                """.formatted(
                expectedTomorrow.minusDays(1),
                expectedTomorrow
        );

        server.expect(request -> {
            String query = request.getURI().getQuery();

            assertTrue(
                    List.of(query.split("&"))
                            .contains("timezone=Asia/Seoul")
            );
        }).andRespond(
                withSuccess(json, MediaType.APPLICATION_JSON)
        );

        WeatherForecast forecast = service.fetchTomorrow(
                City.SEOUL,
                code -> {
                    assertEquals(2, code);
                    return WeatherCondition.CLOUDY;
                }
        );

        assertEquals(expectedTomorrow, forecast.date());
        assertEquals(City.SEOUL, forecast.city());
        assertEquals(WeatherCondition.CLOUDY, forecast.condition());
        assertEquals(1, forecast.minTemp());
        assertEquals(9, forecast.maxTemp());

        server.verify();
    }

    @Test
    void beforeKoreanMidnight() {
        verifyTomorrowForecast(
                "2026-11-27T14:59:59Z",
                LocalDate.of(2026, 11, 28)
        );
    }

    @Test
    void atKoreanMidnight() {
        verifyTomorrowForecast(
                "2026-11-27T15:00:00Z",
                LocalDate.of(2026, 11, 29)
        );
    }

    @Test
    void koreanNewYearBoundary() {
        verifyTomorrowForecast(
                "2026-12-31T15:00:00Z",
                LocalDate.of(2027, 1, 2)
        );
    }
}