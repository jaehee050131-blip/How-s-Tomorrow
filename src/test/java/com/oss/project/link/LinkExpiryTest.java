package com.oss.project.link;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkExpiryTest {

    private static final LocalDate FORECAST_DATE = LocalDate.of(2026, 11, 21);

    // A clock stopped at the given Korea time, on a server that runs in UTC.
    private static Clock utcServerClockAt(String koreaTime) {
        Instant instant = OffsetDateTime.parse(koreaTime + "+09:00").toInstant();
        return Clock.fixed(instant, ZoneOffset.UTC);
    }

    @Test
    void notExpiredOnTheDayTheLinkWasCreated() {
        assertFalse(LinkExpiry.isExpired(FORECAST_DATE, utcServerClockAt("2026-11-20T18:00:00")));
    }

    @Test
    void notExpiredAtTheLastSecondOfTheForecastDate() {
        assertFalse(LinkExpiry.isExpired(FORECAST_DATE, utcServerClockAt("2026-11-21T23:59:59")));
    }

    @Test
    void expiredAtMidnightAfterTheForecastDate() {
        assertTrue(LinkExpiry.isExpired(FORECAST_DATE, utcServerClockAt("2026-11-22T00:00:00")));
    }

    @Test
    void sameResultWhenTheServerUsesUtcOrKoreaTime() {
        // Midnight on Nov 22 in Korea is still 15:00 on Nov 21 in UTC.
        Instant koreaMidnight = Instant.parse("2026-11-21T15:00:00Z");
        Clock utcServer = Clock.fixed(koreaMidnight, ZoneOffset.UTC);
        Clock koreaServer = Clock.fixed(koreaMidnight, LinkExpiry.KOREA);

        assertTrue(LinkExpiry.isExpired(FORECAST_DATE, utcServer));
        assertTrue(LinkExpiry.isExpired(FORECAST_DATE, koreaServer));
    }
}