package com.roleledger.controller;

import com.roleledger.entity.User;
import com.roleledger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    @GetMapping("/")
    public String home() {
        return "Login successful!";
    }
}