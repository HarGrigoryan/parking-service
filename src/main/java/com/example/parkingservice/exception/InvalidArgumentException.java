package com.example.parkingservice.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String s) {
        super(s);
    }
}
