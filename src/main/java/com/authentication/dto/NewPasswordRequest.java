package com.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NewPasswordRequest {

    @NotBlank(message = "Password should not be empty!!")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
    private String newPassword;

    @NotBlank(message = "Password should not be empty!!")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
    private String confirmPassword;
}
