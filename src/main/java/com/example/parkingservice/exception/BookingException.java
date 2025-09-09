package com.example.parkingservice.exception;

public class BookingException extends RuntimeException{
    public BookingException(String formatted) {
        super(formatted);
    }
}
