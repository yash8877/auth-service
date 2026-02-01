//package com.authentication.servicetest;
//
//import com.authentication.service.MyUserDetailsService;
//import com.authentication.utility.JwtUtil;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.util.ReflectionTestUtils;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class JwtUtilTest {
//
//    private JwtUtil jwtUtil;
//    private MyUserDetailsService userDetailsService;
//
//    private final String secret = Base64.getEncoder().encodeToString("MyVerySecretKeyForJWTThatIsAtLeast256Bits!".getBytes());
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = new JwtUtil();
//        userDetailsService = mock(MyUserDetailsService.class);
//
//        // Inject secret key and userDetailsService using reflection
//        ReflectionTestUtils.setField(jwtUtil, "secretkey", secret);
//        ReflectionTestUtils.setField(jwtUtil, "userDetailsService", userDetailsService);
//    }
//
//    @Test
//    void generateToken_andExtractClaims_shouldWorkCorrectly() {
//        String username = "yash_dev";
//        GrantedAuthority authority = () -> "ROLE_USER";
//        UserDetails userDetails = mock(UserDetails.class);
//        when(userDetails.getUsername()).thenReturn(username);
//        doReturn(Collections.singletonList(authority)).when(userDetails).getAuthorities();
//        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//
//        String token = jwtUtil.generateToken(username);
//
//        assertNotNull(token);
//        assertEquals(username, jwtUtil.extractUserName(token));
//        assertEquals("ROLE_USER", jwtUtil.extractRoles(token));
//        assertTrue(jwtUtil.validateToken(token));
//    }
//
//    @Test
//    void validateToken_shouldReturnFalse_whenExpired() throws InterruptedException {
//        String expiredToken = Jwts.builder()
//                .subject("expiredUser")
//                .expiration(new Date(System.currentTimeMillis() - 1000)) // Already expired
//                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)))
//                .compact();
//
//        ReflectionTestUtils.setField(jwtUtil, "secretkey", secret);
//
//        assertFalse(jwtUtil.validateToken(expiredToken));
//    }
//}