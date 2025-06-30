package com.careercrafter.service;

import com.careercrafter.dto.ResumeRequest;
import com.careercrafter.dto.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    ResumeResponse uploadResume(ResumeRequest resumeRequest, MultipartFile file);
    List<ResumeResponse> getResumesByUser(Long userId);
    ResumeResponse getActiveResumeByUser(Long userId);
    ResumeResponse updateResume(Long resumeId, ResumeRequest resumeRequest, MultipartFile file);
    void deactivateResume(Long resumeId);
}