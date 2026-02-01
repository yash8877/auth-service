package com.authentication.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email should not bu empty!!")
    private String email;
    @NotBlank(message = "Password should not be empty!!")
    private String password;


}