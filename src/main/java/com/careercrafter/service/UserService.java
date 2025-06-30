package com.careercrafter.service;

import com.careercrafter.dto.RegisterRequest;
import com.careercrafter.dto.UserResponse;
import com.careercrafter.model.User;

import java.util.List;

public interface UserService {
    UserResponse registerUser(RegisterRequest registerRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long userId);
    void deactivateUser(Long userId);
    User getAuthenticatedUser();
}