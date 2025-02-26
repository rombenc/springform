package com.yourstech.springform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Email(message = "Enter valid email address")
    private String email;
    @NotBlank(message = "password cannot be empty")
    private String password;
}
