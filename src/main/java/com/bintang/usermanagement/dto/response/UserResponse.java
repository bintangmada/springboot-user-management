package com.bintang.usermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;

}
