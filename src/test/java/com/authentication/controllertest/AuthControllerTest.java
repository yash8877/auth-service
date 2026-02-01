//package com.authentication.controllertest;
//
//import com.authentication.controller.AuthController;
//import com.authentication.dto.LoginRequest;
//import com.authentication.dto.RegisterRequest;
//import com.authentication.service.AuthService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private AuthService authService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void login_Success() throws Exception {
//        LoginRequest request = new LoginRequest();
//        request.setEmail("user@example.com");
//        request.setPassword("password123");
//
//        when(authService.login(any(LoginRequest.class))).thenReturn("mock-jwt-token");
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
//    }
//
//    @Test
//    void login_InvalidCredentials() throws Exception {
//        LoginRequest request = new LoginRequest();
//        request.setEmail("wrong@example.com");
//        request.setPassword("badpassword");
//
//        when(authService.login(any(LoginRequest.class))).thenReturn(null);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    void register_Success() throws Exception {
//        RegisterRequest request = new RegisterRequest();
//        request.setEmail("new@example.com");
//        request.setName("Yash");
//        request.setPassword("password123");
//        request.setAge(25);
//        request.setGender("Male");
//        request.setRoles(Arrays.asList("ROLE_USER"));
//
//        when(authService.register(any(RegisterRequest.class))).thenReturn(true);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("User registered successfully."));
//    }
//
//    @Test
//    void register_UserAlreadyExists() throws Exception {
//        RegisterRequest request = new RegisterRequest();
//        request.setEmail("existing@example.com");
//        request.setName("Yash");
//        request.setPassword("password123");
//        request.setAge(25);
//        request.setGender("Male");
//        request.setRoles(Arrays.asList("ROLE_USER"));
//
//        when(authService.register(any(RegisterRequest.class))).thenReturn(false);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isConflict());
//    }
//
//    @Test
//    void getUserRoles_ReturnsRoles() throws Exception {
//        when(authService.findRoles("john")).thenReturn("ADMIN");
//
//        mockMvc.perform(get("/auth/get-roles/john"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("ADMIN"));
//    }
//
//    @Test
//    void deleteUser_ReturnsMessage() throws Exception {
//        when(authService.deleteUser(1L)).thenReturn("User deleted");
//
//        mockMvc.perform(delete("/auth/delete/id/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User deleted"));
//    }
//}