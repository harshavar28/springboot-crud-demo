package com.example.employeeapp.controller;

import com.example.employeeapp.dto.LoginRequest;
import com.example.employeeapp.dto.RegisterRequest;
import com.example.employeeapp.dto.UserResponse;
import com.example.employeeapp.model.User;
import com.example.employeeapp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {

        if (userRepo.existsByEmail(req.email)) {
            return "User already exists";
        }

        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(req.password); 

        userRepo.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
public UserResponse login(@RequestBody LoginRequest req) {

    User user = userRepo.findByEmail(req.email)
            .filter(u -> u.getPassword().equals(req.password))
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    UserResponse res = new UserResponse();
    res.id = user.getId();
    res.name = user.getName();
    res.email = user.getEmail();

    return res;
}
} 
