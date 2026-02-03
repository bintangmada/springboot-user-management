package com.bintang.usermanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Schema(description = "Standard api response")
@Data
@Builder
public class ApiResponse<T> {

    @Schema(example = "SUCCESS")
    private String status;

    @Schema(example = "User created successfully")
    private String message;

    @Schema(example = "2026-02-01T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "Actual response data, varies by endpoint")
    private T data;

    public static <T> ApiResponse<T> success(String message, T data){
        return ApiResponse.<T>builder()
                .status("SUCCESS")
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
}
