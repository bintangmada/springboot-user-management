package com.bintang.usermanagement;

import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.entity.User;
import com.bintang.usermanagement.exception.DuplicateResourceException;
import com.bintang.usermanagement.repository.UserRepository;
import com.bintang.usermanagement.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
