package com.bintang.usermanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "User update request payload")
@Data
public class UpdateUserRequest {

    @Schema(example = "John Doe")
    @NotBlank
    private String name;

    @Schema(example = "john.doe@email.com")
    @NotBlank
    @Email
    private String email;
}
