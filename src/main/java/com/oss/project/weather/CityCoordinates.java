package com.oss.project.weather;

import com.oss.project.model.City;

public record CityCoordinates(
        double latitude,
        double longitude
) {

    public static CityCoordinates fromCity(City city) {
        return switch (city) {
            case SEOUL -> new CityCoordinates(37.5665, 126.9780);
            case BUSAN -> new CityCoordinates(35.1796, 129.0756);
            case DAEGU -> new CityCoordinates(35.8714, 128.6014);
            case INCHEON -> new CityCoordinates(37.4563, 126.7052);
            case GWANGJU -> new CityCoordinates(35.1595, 126.8526);
            case DAEJEON -> new CityCoordinates(36.3504, 127.3845);
            case ULSAN -> new CityCoordinates(35.5384, 129.3114);
        };
    }
}