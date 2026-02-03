package com.bintang.usermanagement;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.entity.User;
import com.bintang.usermanagement.exception.DuplicateResourceException;
import com.bintang.usermanagement.exception.ResourceNotFoundException;
import com.bintang.usermanagement.repository.UserRepository;
import com.bintang.usermanagement.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    // ================= CREATE =================
    @Test
    void create_success() {
        CreateUserRequest request = CreateUserRequest.builder()
                .name("john")
                .email("john@mail.com")
                .build();

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        UserResponse response = userService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Bintang", response.getName());
        assertEquals("bintang@mail.com", response.getEmail());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_duplicateEmail_shouldThrowException() {
        CreateUserRequest request = CreateUserRequest.builder()
                .email("bintang@mail.com")
                .build();

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        DuplicateResourceException ex = assertThrows(
                DuplicateResourceException.class,
                () -> userService.create(request)
        );

        assertEquals("email", ex.getField());
        assertEquals("email already exists", ex.getMessage());

        verify(userRepository, never()).save(any());
    }

    // ================= GET BY ID =================

    @Test
    void getById_success() {
        User user = User.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Bintang", response.getName());
        assertEquals("bintang@mail.com", response.getEmail());
    }

    @Test
    void getById_notFound_shouldThrowException() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getById(1L)
        );
    }

    // ================= SEARCH =================

    @Test
    void search_success() {
        Pageable pageable = PageRequest.of(0, 10);

        User user = User.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);

        when(userRepository.findAll(
                ArgumentMatchers.<Specification<User>>any(),
                eq(pageable))
        ).thenReturn(page);

        Page<UserResponse> result =
                userService.search("Bintang", "bintang@mail.com", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Bintang", result.getContent().get(0).getName());
    }

    // ================= GET ALL =================

    @Test
    void getAll_success() {
        Pageable pageable = PageRequest.of(0, 10);

        User user = User.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);

        when(userRepository.findAll(pageable))
                .thenReturn(page);

        Page<UserResponse> result = userService.getAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Bintang", result.getContent().get(0).getName());
    }

    // ================= UPDATE =================

    @Test
    void update_success() {
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("Updated Name")
                .email("updated@mail.com")
                .build();

        User user = User.builder()
                .id(1L)
                .name("Old Name")
                .email("old@mail.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByEmailAndIdNot(request.getEmail(), 1L))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.update(1L, request);

        assertEquals("Updated Name", response.getName());
        assertEquals("updated@mail.com", response.getEmail());
    }

    @Test
    void update_duplicateEmail_shouldThrowException() {
        UpdateUserRequest request = UpdateUserRequest.builder()
                .email("dup@mail.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User()));

        when(userRepository.existsByEmailAndIdNot(request.getEmail(), 1L))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> userService.update(1L, request)
        );
    }
}
