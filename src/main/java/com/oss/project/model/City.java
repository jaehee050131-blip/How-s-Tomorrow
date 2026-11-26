package com.oss.project.model;

public enum City {

    SEOUL("seoul", "서울"),
    BUSAN("busan", "부산"),
    DAEGU("daegu", "대구"),
    INCHEON("incheon", "인천"),
    GWANGJU("gwangju", "광주"),
    DAEJEON("daejeon", "대전"),
    ULSAN("ulsan", "울산");

    private final String code;
    private final String koreanName;

    City(String code, String koreanName) {
        this.code = code;
        this.koreanName = koreanName;
    }

    public String getCode() {
        return code;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static City fromCode(String code) {
        for (City city : values()) {
            if (city.code.equals(code)) {
                return city;
            }
        }

        throw new IllegalArgumentException(
                "Unknown city code: " + code
        );
    }
}