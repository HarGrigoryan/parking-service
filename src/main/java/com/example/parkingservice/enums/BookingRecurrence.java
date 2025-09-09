package com.example.parkingservice.enums;

public enum BookingRecurrence {

    NONE,
    EVERY_DAY,
    EVERY_WEEK,
    EVERY_MONTH;

    public int getDayCount() {
        return switch (this) {
            case EVERY_DAY -> 1;
            case EVERY_WEEK -> 7;
            case EVERY_MONTH -> 30;
            default -> 0;
        };
    }

}
