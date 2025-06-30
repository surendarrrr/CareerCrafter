package com.careercrafter.dto;

import com.careercrafter.model.Resume;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private String skills;
    private String experience;
    private String education;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;

    public static ResumeResponse mapToResumeResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .userId(resume.getUser().getId())
                .userName(resume.getUser().getFirstName() + " " + resume.getUser().getLastName())
                .fileName(resume.getFileName())
                .filePath(resume.getFilePath())
                .fileSize(resume.getFileSize())
                .contentType(resume.getContentType())
                .skills(resume.getSkills())
                .experience(resume.getExperience())
                .education(resume.getEducation())
                .createdDate(resume.getCreatedDate())
                .updatedDate(resume.getUpdatedDate())
                .isActive(resume.isActive())
                .build();
    }
}