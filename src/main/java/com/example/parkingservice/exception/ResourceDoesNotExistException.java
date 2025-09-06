package com.example.parkingservice.exception;

public class ResourceDoesNotExistException extends RuntimeException {
    public ResourceDoesNotExistException(String message) {
        super(message);
    }

    public ResourceDoesNotExistException(Long id, String resourceName) {
        super(resourceName + " with id [%s] was not found".formatted(id));
    }
}
