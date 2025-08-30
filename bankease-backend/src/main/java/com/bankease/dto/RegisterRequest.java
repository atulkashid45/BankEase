package com.bankease.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank public String name;
    @Email public String email;
    @NotBlank public String password;
}
