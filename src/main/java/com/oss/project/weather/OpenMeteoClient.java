package com.oss.project.weather;

import com.oss.project.model.City;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Objects;

@Component
public class OpenMeteoClient {

    private final RestClient restClient;

    public OpenMeteoClient() {
        this(createRestClient());
    }

    OpenMeteoClient(RestClient restClient) {
        this.restClient = restClient;
    }

    private static RestClient createRestClient() {
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        return RestClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .requestFactory(factory)
                .build();
    }

    public OpenMeteoResponse fetchForecast(City city) {
        Objects.requireNonNull(city, "City is required");

        CityCoordinates coordinates =
                CityCoordinates.fromCity(city);

        OpenMeteoResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", coordinates.latitude())
                        .queryParam("longitude", coordinates.longitude())
                        .queryParam("daily",
                                "weather_code,temperature_2m_min,temperature_2m_max")
                        .queryParam("timezone", "Asia/Seoul")
                        .queryParam("forecast_days", 2)
                        .build())
                .retrieve()
                .body(OpenMeteoResponse.class);

        if (response == null || response.daily() == null) {
            throw new RestClientException(
                    "Open-Meteo returned an empty forecast"
            );
        }

        return response;
    }
}