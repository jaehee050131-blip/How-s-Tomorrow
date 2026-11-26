package com.oss.project.link;

import com.oss.project.model.City;
import com.oss.project.model.WeatherCondition;
import com.oss.project.model.WeatherForecast;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherLinkTest {

    private static final LocalDate DATE = LocalDate.of(2026, 11, 21);

    // Reads the values out of a link, the same way Spring does when the link is opened.
    private static Map<String, String> valuesOf(String link) {
        return UriComponentsBuilder.fromUriString(link).build().getQueryParams().toSingleValueMap();
    }

    // A valid set of link values. Each invalid test changes one of them.
    private static Map<String, String> validValues() {
        Map<String, String> values = new HashMap<>();
        values.put("city", "seoul");
        values.put("date", "2026-11-21");
        values.put("weather", "rainy");
        values.put("min", "3");
        values.put("max", "9");
        return values;
    }

    // ---------- Round trip: create, then read, gives the same forecast ----------

    @Test
    void roundTripNormalForecast() {
        WeatherForecast forecast = new WeatherForecast(DATE, City.SEOUL, WeatherCondition.RAINY, 3, 9);
        assertEquals(forecast, WeatherLink.read(valuesOf(WeatherLink.create(forecast))));
    }

    @Test
    void roundTripBelowZero() {
        WeatherForecast forecast = new WeatherForecast(DATE, City.SEOUL, WeatherCondition.SNOWY, -7, -1);
        assertEquals(forecast, WeatherLink.read(valuesOf(WeatherLink.create(forecast))));
    }

    @Test
    void sameMinAndMaxIsInvalid() {
        Map<String, String> values = validValues();
        values.put("min", "5");
        values.put("max", "5");

        assertThrows(IllegalArgumentException.class,
                () -> WeatherLink.read(values));
    }

    @Test
    void createdLinkHasTheAgreedFormat() {
        WeatherForecast forecast = new WeatherForecast(DATE, City.SEOUL, WeatherCondition.RAINY, 3, 9);
        assertEquals("/view?city=seoul&date=2026-11-21&weather=rainy&min=3&max=9",
                WeatherLink.create(forecast));
    }

    // ---------- Invalid links: every case throws IllegalArgumentException ----------

    @Test
    void missingValueIsInvalid() {
        Map<String, String> values = validValues();
        values.remove("weather");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void emptyValueIsInvalid() {
        Map<String, String> values = validValues();
        values.put("city", "");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void unknownCityIsInvalid() {
        Map<String, String> values = validValues();
        values.put("city", "tokyo");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void unknownWeatherIsInvalid() {
        Map<String, String> values = validValues();
        values.put("weather", "stormy");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void temperatureThatIsNotANumberIsInvalid() {
        Map<String, String> values = validValues();
        values.put("min", "abc");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void minHigherThanMaxIsInvalid() {
        Map<String, String> values = validValues();
        values.put("min", "10");
        values.put("max", "3");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }

    @Test
    void invalidDateIsInvalid() {
        Map<String, String> values = validValues();
        values.put("date", "2026-13-40");
        assertThrows(IllegalArgumentException.class, () -> WeatherLink.read(values));
    }
}