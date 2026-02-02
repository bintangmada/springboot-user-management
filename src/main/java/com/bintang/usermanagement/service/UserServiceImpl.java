package com.bintang.usermanagement.service;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.entity.User;
import com.bintang.usermanagement.exception.DuplicateResourceException;
import com.bintang.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email", "email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getById(Long id) {
        return null;
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
