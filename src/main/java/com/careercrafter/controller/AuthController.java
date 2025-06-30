package com.careercrafter.controller;

import com.careercrafter.dto.LoginRequest;
import com.careercrafter.dto.RegisterRequest;
import com.careercrafter.dto.UserResponse;
import com.careercrafter.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authenticationService.logout();
        return ResponseEntity.ok("Logged out successfully");
    }
}