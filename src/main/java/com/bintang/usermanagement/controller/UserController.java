package com.bintang.usermanagement.controller;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.ApiResponse;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User Management",
        description = "Endpoints for managing users including CRUD operations"
)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create new user",
            description = "Create a new user with validated request body"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Duplicate user")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@RequestBody @Valid CreateUserRequest request) {
        UserResponse userResponse = userService.create(request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User created successfully", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieve paginated list of users"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserResponse> userResponsePageable = userService.getAll(pageable);
        ApiResponse<Page<UserResponse>> apiResponse = ApiResponse.success("Users retrieved successfully", userResponsePageable);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve user details by user ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @PathVariable("id")
            @Parameter(description = "User ID", example = "1")
            Long id
    ) {
        UserResponse user = userService.getById(id);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User retrieved successfully", user);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(
            summary = "Update existing user",
            description = "Update user details by ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable("id")
            @Parameter(description = "User ID", example = "1")
            Long id,

            @RequestBody @Valid UpdateUserRequest request
    ) {
        UserResponse updatedUser = userService.update(id, request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.success("User updated successfully", updatedUser);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Page<UserResponse> userResponse = userService.search(name, email, pageable);
        return ResponseEntity.ok().body(ApiResponse.success("User found", userResponse));
    }


    @Operation(
            summary = "Delete user",
            description = "Delete user by ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<UserResponse>> delete(
            @PathVariable("id")
            @Parameter(description = "User ID", example = "1")
            Long id
    ) {
        userService.delete(id);
        return ResponseEntity.ok()
                .body(ApiResponse.success(
                        "User deleted successfully",
                        UserResponse.builder().build()));
    }

}
