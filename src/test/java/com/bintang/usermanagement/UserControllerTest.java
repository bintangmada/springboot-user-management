package com.bintang.usermanagement;

import com.bintang.usermanagement.controller.UserController;
import com.bintang.usermanagement.dto.request.CreateUserRequest;
import com.bintang.usermanagement.dto.request.UpdateUserRequest;
import com.bintang.usermanagement.dto.response.UserResponse;
import com.bintang.usermanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_success() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        when(userService.create(any(CreateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.message").value("User created successfully"))
                .andExpect((ResultMatcher) jsonPath("$.data.id").value(1))
                .andExpect((ResultMatcher) jsonPath("$.data.name").value("Bintang"));
    }

    @Test
    void getAllUsers_success() throws Exception {
        UserResponse user = UserResponse.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        Page<UserResponse> page = new PageImpl<>(List.of(user));

        when(userService.getAll(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect((ResultMatcher) jsonPath("$.data.content[0].id").value(1));
    }

    @Test
    void getUserById_success() throws Exception {
        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        when(userService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.id").value(1))
                .andExpect((ResultMatcher) jsonPath("$.data.name").value("Bintang"));
    }

    @Test
    void updateUser_success() throws Exception {
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("Bintang Update")
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .name("Bintang Update")
                .build();

        when(userService.update(eq(1L), any(UpdateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("User updated successfully"))
                .andExpect((ResultMatcher) jsonPath("$.data.name").value("Bintang Update"));
    }

    @Test
    void searchUser_success() throws Exception {
        UserResponse user = UserResponse.builder()
                .id(1L)
                .name("Bintang")
                .email("bintang@mail.com")
                .build();

        Page<UserResponse> page = new PageImpl<>(List.of(user));

        when(userService.search(any(), any(), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/users/search")
                        .param("name", "Bintang"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("User found"))
                .andExpect((ResultMatcher) jsonPath("$.data.content[0].name").value("Bintang"));
    }

    @Test
    void deleteUser_success() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.message").value("User deleted successfully"));
    }

}

