package com.authentication.controller;

import com.authentication.dto.AuthResponse;
import com.authentication.dto.LoginRequest;
import com.authentication.dto.NewPasswordRequest;
import com.authentication.dto.RegisterRequest;
import com.authentication.exception.InvalidCredentialException;
import com.authentication.exception.UserAlreadyExists;
import com.authentication.exception.UserNotFoundException;
import com.authentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        boolean flag = authService.register(request);
        if (flag) {
            log.info("User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        }
        throw new UserAlreadyExists("User is already registered with the email: " + request.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        if (token == null) {
            throw new InvalidCredentialException("Invalid credentials.");
        }
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PatchMapping("/reset-password/{email}")
    public ResponseEntity<?> resetUserPassword(@PathVariable String email,@Valid @RequestBody NewPasswordRequest newPasswordRequest){
        if (!newPasswordRequest.getNewPassword().equals(newPasswordRequest.getConfirmPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The new password is not matched with confirm password!!");
        }
        if(authService.resetPassword(email,newPasswordRequest)){
            return ResponseEntity.status(HttpStatus.OK).body("User password has been changed successfully.");
        }
        throw new UserNotFoundException("No User Found with this email: "+email);
    }




    @GetMapping("/get-roles/{username}")
    public ResponseEntity<String> getUserRoles(@PathVariable String username){
        return new ResponseEntity<>(authService.findRoles(username),HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{userId}")
    public String deleteUser(@PathVariable Long userId){
        return authService.deleteUser(userId);
    }

}