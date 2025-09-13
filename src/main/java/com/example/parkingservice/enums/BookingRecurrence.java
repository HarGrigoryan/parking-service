package com.example.parkingservice.enums;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public enum BookingRecurrence {

    NONE,
    EVERY_DAY,
    EVERY_WEEK,
    EVERY_MONTH;


    public Instant getNextInstant(Instant x) {
        return switch (this) {
            case EVERY_DAY ->
                    x.plus(Duration.ofDays(1));
            case EVERY_WEEK ->
                    x.plus(Duration.ofDays(7));
            case EVERY_MONTH ->
                x.atZone(ZoneId.systemDefault()).plusMonths(1).toInstant();
            default -> x;
        };
    }

}
