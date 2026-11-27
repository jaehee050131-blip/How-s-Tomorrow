package com.oss.project.weather;

import com.oss.project.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class OpenMeteoClientTest {

    private MockRestServiceServer server;
    private OpenMeteoClient client;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://api.open-meteo.com");

        server = MockRestServiceServer.bindTo(builder).build();

        client = new OpenMeteoClient(builder.build());
    }

    @Test
    void fetchValidForecast() {
        String json = """
                {
                  "daily": {
                    "time": ["2026-11-27", "2026-11-28"],
                    "weather_code": [2, 2],
                    "temperature_2m_min": [6.1, 0.8],
                    "temperature_2m_max": [15.1, 8.7]
                  }
                }
                """;

        server.expect(queryParam("latitude", "37.5665"))
                .andExpect(queryParam("longitude", "126.978"))
                .andExpect(queryParam("forecast_days", "2"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        OpenMeteoResponse response =
                client.fetchForecast(City.SEOUL);

        assertEquals("2026-11-28", response.daily().time().get(1));
        assertEquals(2, response.daily().weatherCode().get(1));
        assertEquals(0.8, response.daily().minTemperature().get(1));
        assertEquals(8.7, response.daily().maxTemperature().get(1));

        server.verify();
    }

    @Test
    void handleApiFailure() {
        server.expect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        assertThrows(RestClientException.class,
                () -> client.fetchForecast(City.SEOUL));

        server.verify();
    }

    @Test
    void handleMissingDailyData() {
        server.expect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        assertThrows(RestClientException.class,
                () -> client.fetchForecast(City.SEOUL));

        server.verify();
    }

    @Test
    void rejectNullCity() {
        assertThrows(NullPointerException.class,
                () -> client.fetchForecast(null));
    }
    @Test
    @org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable(
            named = "LIVE_WEATHER_TEST",
            matches = "true"
    )
    void fetchLiveForecast() {
        OpenMeteoClient liveClient = new OpenMeteoClient();

        OpenMeteoResponse response =
                liveClient.fetchForecast(City.SEOUL);

        String tomorrow = java.time.LocalDate
                .now(java.time.ZoneId.of("Asia/Seoul"))
                .plusDays(1)
                .toString();

        assertNotNull(response.daily());

        int index = response.daily().time().indexOf(tomorrow);

        assertTrue(index >= 0);

        assertNotNull(response.daily().weatherCode().get(index));
        assertNotNull(response.daily().minTemperature().get(index));
        assertNotNull(response.daily().maxTemperature().get(index));

        System.out.println("Live API response: " + response);
    }
}