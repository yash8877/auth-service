package com.authentication.dto;


import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterRequest {


    @Column(unique = true)
    @NotBlank(message = "Email should not be null or empty!!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message = "Password should not be null or empty")

    @NotBlank(message = "Password should not be empty!!")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
    private String password;

    @NotBlank(message = "Name should not be empty!!!")
    @Size(min = 3,max=50,message = "Name should be greater than 3 characters!")
    private String name;


    @NotBlank(message = "Gender should not be blank!")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other!")
    private String gender;

    @Min(value = 18, message = "Age must be at least 18 years old!")
    @Max(value = 100, message = "Age must not exceed 100 years!")
    private int age;

    @NotBlank(message = "Phone number should not be blank!")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number! It should be a valid 10-digit number starting with 6, 7, 8, or 9.")
    private String number;


    private List<String> roles;
}
