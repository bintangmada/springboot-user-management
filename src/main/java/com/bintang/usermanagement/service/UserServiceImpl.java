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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> search(String name, String email, Pageable pageable) {
        Specification<User> spec = UserSpecification.filter(name, email);
        return userRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if(userRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new DuplicateResourceException("email", "email already exists");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setDeleted(true);
        userRepository.save(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
