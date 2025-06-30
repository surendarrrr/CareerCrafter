package com.careercrafter.service;

import com.careercrafter.dto.JobApplicationRequest;
import com.careercrafter.dto.JobResponse;

import java.util.List;

public interface JobApplicationService {
    JobResponse applyForJob(JobApplicationRequest jobApplicationRequest);
    List<JobResponse> getApplicationsByJobSeeker(Long jobSeekerId);
    List<JobResponse> getApplicationsByJob(Long jobId);
    void withdrawApplication(Long applicationId);
    void updateApplicationStatus(Long applicationId, String status);
}