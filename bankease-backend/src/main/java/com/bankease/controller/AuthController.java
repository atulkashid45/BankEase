package com.bankease.controller;

import com.bankease.dto.AuthRequest;
import com.bankease.dto.AuthResponse;
import com.bankease.dto.RegisterRequest;
import com.bankease.model.User;
import com.bankease.security.JwtService;
import com.bankease.service.CustomUserDetailsService;
import com.bankease.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        User u = userService.register(req);
        String token = jwtService.generateToken(u.getUsername(), Map.of("name", u.getName()));
        return ResponseEntity.ok(new AuthResponse(token, u.getName(), u.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email, req.password));
        User u = (User) auth.getPrincipal();
        String token = jwtService.generateToken(u.getUsername(), Map.of("name", u.getName()));
        return ResponseEntity.ok(new AuthResponse(token, u.getName(), u.getEmail()));
    }
}
