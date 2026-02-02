package com.bintang.usermanagement.controller;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.response.ApiResponse;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid CreateUserRequest request){
        UserResponse userResponse = userService.create(request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User created successfully", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
