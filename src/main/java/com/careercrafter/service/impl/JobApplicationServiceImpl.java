package com.careercrafter.service.impl;

import com.careercrafter.dto.JobApplicationRequest;
import com.careercrafter.dto.JobResponse;
import com.careercrafter.model.Job;
import com.careercrafter.model.JobApplication;
import com.careercrafter.model.User;
import com.careercrafter.repository.JobApplicationRepository;
import com.careercrafter.repository.JobRepository;
import com.careercrafter.repository.UserRepository;
import com.careercrafter.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    
    @Override
    public JobResponse applyForJob(JobApplicationRequest jobApplicationRequest) {
        Job job = jobRepository.findById(jobApplicationRequest.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        User jobSeeker = userRepository.findById(jobApplicationRequest.getJobSeekerId())
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        
        if (jobApplicationRepository.existsByJobAndJobSeeker(job, jobSeeker)) {
            throw new RuntimeException("You have already applied for this job");
        }
        
        JobApplication application = JobApplication.builder()
                .job(job)
                .jobSeeker(jobSeeker)
                .coverLetter(jobApplicationRequest.getCoverLetter())
                .build();
        
        jobApplicationRepository.save(application);
        return JobResponse.mapToJobResponse(job);
    }
    
    @Override
    public List<JobResponse> getApplicationsByJobSeeker(Long jobSeekerId) {
        User jobSeeker = userRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        
        return jobApplicationRepository.findByJobSeeker(jobSeeker).stream()
                .map(application -> JobResponse.mapToJobResponse(application.getJob()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<JobResponse> getApplicationsByJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        return jobApplicationRepository.findByJob(job).stream()
                .map(application -> JobResponse.mapToJobResponse(application.getJob()))
                .collect(Collectors.toList());
    }
    
    @Override
    public void withdrawApplication(Long applicationId) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        jobApplicationRepository.delete(application);
    }
    
    @Override
    public void updateApplicationStatus(Long applicationId, String status) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        try {
            JobApplication.Status newStatus = JobApplication.Status.valueOf(status.toUpperCase());
            application.setStatus(newStatus);
            jobApplicationRepository.save(application);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }
    }
}