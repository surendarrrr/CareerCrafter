package com.careercrafter.service;

import com.careercrafter.dto.LoginRequest;
import com.careercrafter.dto.RegisterRequest;
import com.careercrafter.dto.UserResponse;

import java.util.Map;

public interface AuthenticationService {
    UserResponse register(RegisterRequest request);
    Map<String, String> login(LoginRequest request);
    void logout();
}