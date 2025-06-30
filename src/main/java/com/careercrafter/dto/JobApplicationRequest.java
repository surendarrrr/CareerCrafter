package com.careercrafter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobApplicationRequest {
    @NotNull(message = "Job ID is required")
    private Long jobId;
    
    @NotNull(message = "Job seeker ID is required")
    private Long jobSeekerId;
    
    private String coverLetter;
}