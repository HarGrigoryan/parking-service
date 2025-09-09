package com.example.parkingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> resourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder().withMessage(e.getMessage()).
                withStatus(HttpStatus.BAD_REQUEST).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity<ExceptionResponse> resourceDoesNotExistException(ResourceDoesNotExistException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder().withMessage(e.getMessage()).
                withStatus(HttpStatus.BAD_REQUEST).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ExceptionResponse> invalidArgumentException(InvalidArgumentException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .withMessage(e.getMessage())
                .withStatus(HttpStatus.BAD_REQUEST)
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ExceptionResponse> bookingException(BookingException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .withMessage(e.getMessage())
                .withStatus(HttpStatus.BAD_REQUEST)
                .build(),
                HttpStatus.BAD_REQUEST);
    }

}
