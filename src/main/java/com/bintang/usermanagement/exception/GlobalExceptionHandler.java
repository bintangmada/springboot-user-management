package com.bintang.usermanagement.exception;

import com.bintang.usermanagement.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                        .add(error.getDefaultMessage()));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        Map<String, List<String>> errors = Map.of(ex.getFieldName(), List.of(ex.getMessage()));
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", request, errors);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request
    ){
        Map<String, List<String>> errors = Map.of(ex.getField(), List.of(ex.getMessage()));
        return buildErrorResponse(HttpStatus.CONFLICT, "Duplicate resource", request, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
        Exception ex,
        HttpServletRequest request
    ){
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request,
                Map.of("error", List.of(ex.getMessage()))
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, List<String>> errors
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .errors(errors)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
