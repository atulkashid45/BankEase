package com.bankease.service;

import com.bankease.dto.RegisterRequest;
import com.bankease.model.Role;
import com.bankease.model.User;
import com.bankease.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new RuntimeException("Email already registered");
        }
        User u = new User(req.name, req.email, passwordEncoder.encode(req.password), Role.USER);
        return userRepository.save(u);
    }
}
