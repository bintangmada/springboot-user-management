package com.bintang.usermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

}
