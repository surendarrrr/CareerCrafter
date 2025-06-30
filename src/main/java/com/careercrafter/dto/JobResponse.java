package com.careercrafter.dto;

import com.careercrafter.model.Job;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String jobType;
    private String experienceLevel;
    private String companyName;
    private Long employerId;
    private String employerName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;

    public static JobResponse mapToJobResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .location(job.getLocation())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .jobType(job.getJobType())
                .experienceLevel(job.getExperienceLevel())
                .companyName(job.getCompanyName())
                .employerId(job.getEmployer().getId())
                .employerName(job.getEmployer().getFirstName() + " " + job.getEmployer().getLastName())
                .createdDate(job.getCreatedDate())
                .updatedDate(job.getUpdatedDate())
                .isActive(job.isActive())
                .build();
    }
}