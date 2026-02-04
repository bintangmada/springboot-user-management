package com.bintang.usermanagement.service;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.entity.User;
import com.bintang.usermanagement.exception.DuplicateResourceException;
import com.bintang.usermanagement.exception.ResourceNotFoundException;
import com.bintang.usermanagement.repository.UserRepository;
import com.bintang.usermanagement.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse create(CreateUserRequest request) {

        log.info("Creating user with email={}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("email", "email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created successfully, id={}, email={}",
                savedUser.getId(), savedUser.getEmail());

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {

        log.info("Fetching user by id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        log.info("User found, id={}, email={}", user.getId(), user.getEmail());

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> search(String name, String email, Pageable pageable) {

        log.info("Searching users with filters name={}, email={}, page={}, size={}",
                name, email, pageable.getPageNumber(), pageable.getPageSize());

        Specification<User> spec = UserSpecification.filter(name, email);
        Page<UserResponse> result = userRepository.findAll(spec, pageable).map(this::mapToResponse);

        log.info("Search completed, totalElements={}", result.getTotalElements());

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAll(Pageable pageable) {

        log.info("Fetching all users, page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<UserResponse> result = userRepository.findAll(pageable).map(this::mapToResponse);

        log.info("Fetched users successfully, totalElements={}",
                result.getTotalElements());

        return result;
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {

        log.info("Starting update user process, id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if(userRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new DuplicateResourceException("email", "email already exists");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully, id={}, email={}",
                updatedUser.getId(), updatedUser.getEmail());

        return mapToResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {

        log.info("Starting delete user process, id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setDeleted(true);

        userRepository.save(user);

        log.info("User deleted (soft delete) successfully, id={}", id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
