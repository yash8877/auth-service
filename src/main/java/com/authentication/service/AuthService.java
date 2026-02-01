package com.authentication.service;

import com.authentication.dto.AuthResponse;
import com.authentication.dto.LoginRequest;
import com.authentication.dto.NewPasswordRequest;
import com.authentication.dto.RegisterRequest;
import com.authentication.entity.User;
import com.authentication.exception.InvalidCredentialException;
import com.authentication.repository.AuthRepository;
import com.authentication.utility.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not exits with this email: "+request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Invalid credentials | Please enter correct password!!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public boolean register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) return false;

        User user = new User();
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setNumber(request.getNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRoles(List.of("USER"));
        userRepository.save(user);

        return true;
    }
    public String findRoles(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.map(user -> user.getRoles().get(0)).orElse(null);
    }

    @Transactional
    public boolean resetPassword(String email, NewPasswordRequest passwordRequest){
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()){
            User user = userByEmail.get();
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            log.info("Password reset for user: {}", email);
            return true;
        }
        return false;
    }


    public String deleteUser(Long userId) {
        userRepository.deleteUserRolesByUserId(userId);
        userRepository.deleteUserById(userId);
        return "deleted";
    }
}
