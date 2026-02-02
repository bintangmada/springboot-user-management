package com.bintang.usermanagement.exception;

import com.bintang.usermanagement.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
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

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Argument not valid")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
