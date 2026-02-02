package com.bintang.usermanagement.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
public class ApiResponse<T> {

    private String status;
    private String message;
    private LocalDateTime timestamp;
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
