//package com.authentication.servicetest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.authentication.dto.LoginRequest;
//import com.authentication.dto.RegisterRequest;
//import com.authentication.entity.User;
//import com.authentication.exception.InvalidCredentialException;
//import com.authentication.exception.UserNotFoundException;
//import com.authentication.repository.AuthRepository;
//import com.authentication.service.AuthService;
//import com.authentication.utility.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//class AuthServiceTest {
//
//    @Mock
//    private AuthRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @InjectMocks
//    private AuthService authService;
//
//    private RegisterRequest registerRequest;
//    private LoginRequest loginRequest;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        user = new User();
//        user.setId(1L);
//        user.setEmail("test@example.com");
//        user.setPassword("encodedPassword");
//        user.setRoles(List.of("USER"));
//
//        registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPassword("password123");
//
//        loginRequest = new LoginRequest();
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword("password123");
//    }
//
//    @Test
//    void testRegister_Success() {
//        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
//        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
//
//        boolean result = authService.register(registerRequest);
//
//        assertTrue(result);
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void testRegister_UserAlreadyExists() {
//        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);
//
//        boolean result = authService.register(registerRequest);
//
//        assertFalse(result);
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void testLogin_Success() {
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
//        when(jwtUtil.generateToken(user.getEmail())).thenReturn("mockJwtToken");
//
//        String token = authService.login(loginRequest);
//
//        assertNotNull(token);
//        assertEquals("mockJwtToken", token);
//    }
//
//    @Test
//    void testLogin_UserNotFound() {
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
//        assertEquals("User not found: test@example.com", exception.getMessage());
//    }
//
//    @Test
//    void testLogin_InvalidPassword() {
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);
//
//        assertThrows(InvalidCredentialException.class, () -> authService.login(loginRequest));
//    }
//
//    @Test
//    void testLogin_JWTGenerationFailure() {
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
//        when(jwtUtil.generateToken(user.getEmail())).thenThrow(new RuntimeException("JWT generation failed"));
//
//        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
//        assertEquals("JWT generation failed", exception.getMessage());
//    }
//}