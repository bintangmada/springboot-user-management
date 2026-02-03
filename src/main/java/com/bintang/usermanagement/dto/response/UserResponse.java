package com.bintang.usermanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "User response payload")
@AllArgsConstructor
@Data
@Builder
public class UserResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "john.doe@email.com")
    private String email;

}
