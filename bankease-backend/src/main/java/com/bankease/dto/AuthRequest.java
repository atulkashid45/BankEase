package com.bankease.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @Email public String email;
    @NotBlank public String password;
}
