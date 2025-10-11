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
    public ResponseEntity<ResponseException> genericExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(
            new ResponseException(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<ResponseException> customNotFoundExceptionHandler(
        CustomBadRequestException ex,
        WebRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseException(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ResponseException> customBadRequestExceptionHandler(
        CustomBadRequestException ex,
        WebRequest request
    ) {
        return ResponseEntity.badRequest().body(
            new ResponseException(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
            )
        );
    }
}
