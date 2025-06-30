package com.careercrafter.service.impl;

import com.careercrafter.dto.LoginRequest;
import com.careercrafter.dto.RegisterRequest;
import com.careercrafter.dto.UserResponse;
import com.careercrafter.exception.UserAlreadyExistsException;
import com.careercrafter.model.User;
import com.careercrafter.repository.UserRepository;
import com.careercrafter.service.AuthenticationService;
import com.careercrafter.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.mapToUserResponse(savedUser);
    }

    @Override
    public Map<String, String> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }

    @Override
    public void logout() {
        // In a real application, you might want to invalidate the token
    }
}