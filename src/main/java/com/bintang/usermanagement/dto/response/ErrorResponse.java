package com.bintang.usermanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "Standard error response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @Schema(example = "404")
    private int code;

    @Schema(example = "User not found")
    private String message;

    @Schema(example = "/api/users")
    private String path;

    @Schema(example = "2026-02-01T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "Validation errors grouped by field name")
    private Map<String, List<String>> errors = new HashMap<>();

}
