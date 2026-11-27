package com.oss.project.link;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

public final class LinkExpiry {

    public static final ZoneId KOREA = ZoneId.of("Asia/Seoul");

    private LinkExpiry() {
    }

    public static boolean isExpired(LocalDate forecastDate, Clock clock) {
        LocalDate todayInKorea = LocalDate.now(clock.withZone(KOREA));
        return todayInKorea.isAfter(forecastDate);
    }
}