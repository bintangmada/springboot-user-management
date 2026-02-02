package com.bintang.usermanagement.service;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(CreateUserRequest request);
    UserResponse getById(Long id);
    Page<UserResponse> getAll(Pageable pageable);
    UserResponse update(Long id, UpdateUserRequest request);
    void delete(Long id);
}
