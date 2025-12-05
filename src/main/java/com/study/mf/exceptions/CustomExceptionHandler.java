package com.study.mf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(
            new ExceptionResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> customNotFoundExceptionHandler(
        CustomResourceNotFoundException ex,
        WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ExceptionResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ExceptionResponse> customBadRequestExceptionHandler(
        CustomBadRequestException ex,
        WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
            new ExceptionResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }
}
