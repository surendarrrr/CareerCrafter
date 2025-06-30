package com.careercrafter.dto;

import com.careercrafter.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private User.Role role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .isActive(user.isActive())
                .build();
    }
}