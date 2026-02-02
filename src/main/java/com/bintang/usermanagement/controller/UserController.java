package com.bintang.usermanagement.controller;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.ApiResponse;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid CreateUserRequest request) {
        UserResponse userResponse = userService.create(request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User created successfully", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable("id") Long id) {
        UserResponse user = userService.getById(id);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User retrieved successfully", user);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserResponse> userResponsePageable = userService.getAll(pageable);
        ApiResponse<Page<UserResponse>> apiResponse = ApiResponse.success("User retrieved successfully", userResponsePageable);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequest request){
        UserResponse updatedUser = userService.update(id, request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User updated successfully", updatedUser);
        return ResponseEntity.ok(apiResponse);
    }


}
