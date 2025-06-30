package com.careercrafter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeRequest {
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "File name is required")
    private String fileName;
    
    @NotBlank(message = "File path is required")
    private String filePath;
    
    private Long fileSize;
    private String contentType;
    private String skills;
    private String experience;
    private String education;
}