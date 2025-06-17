package com.emrekentli.openremotecrud.rest;

import com.emrekentli.openremotecrud.exception.OpenRemoteAuthException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<MetaResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream().findFirst()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .orElse("Validation error");
        log.warn("Validation error: {}", errorMsg);
        return ResponseEntity.badRequest().body(ResponseBuilder.build(MetaResponse.of("400", errorMsg)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<MetaResponse>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ResponseBuilder.build(MetaResponse.of("400", ex.getMessage())));
    }

    @ExceptionHandler(OpenRemoteAuthException.class)
    public ResponseEntity<Response<MetaResponse>> handleAuthException(OpenRemoteAuthException ex) {
        log.error("Auth exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseBuilder.build(MetaResponse.of("401", ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<MetaResponse>> handleGeneralException(Exception ex) {
        log.error("Internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder.build(MetaResponse.of("500", "Internal server error")));
    }
}