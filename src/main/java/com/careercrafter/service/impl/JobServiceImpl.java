package com.careercrafter.service.impl;

import com.careercrafter.dto.JobRequest;
import com.careercrafter.dto.JobResponse;
import com.careercrafter.model.Job;
import com.careercrafter.model.User;
import com.careercrafter.repository.JobRepository;
import com.careercrafter.repository.UserRepository;
import com.careercrafter.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    
    @Override
    public JobResponse postJob(JobRequest jobRequest) {
        User employer = userRepository.findById(jobRequest.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        
        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .requirements(jobRequest.getRequirements())
                .location(jobRequest.getLocation())
                .salaryMin(jobRequest.getSalaryMin())
                .salaryMax(jobRequest.getSalaryMax())
                .jobType(jobRequest.getJobType())
                .experienceLevel(jobRequest.getExperienceLevel())
                .companyName(jobRequest.getCompanyName())
                .employer(employer)
                .build();
        
        Job savedJob = jobRepository.save(job);
        return JobResponse.mapToJobResponse(savedJob);
    }
    
    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findByIsActiveTrue().stream()
                .map(JobResponse::mapToJobResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<JobResponse> getJobsByEmployer(Long employerId) {
        User employer = userRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        
        return jobRepository.findByEmployer(employer).stream()
                .map(JobResponse::mapToJobResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public JobResponse getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return JobResponse.mapToJobResponse(job);
    }
    
    @Override
    public JobResponse updateJob(Long jobId, JobRequest jobRequest) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setRequirements(jobRequest.getRequirements());
        job.setLocation(jobRequest.getLocation());
        job.setSalaryMin(jobRequest.getSalaryMin());
        job.setSalaryMax(jobRequest.getSalaryMax());
        job.setJobType(jobRequest.getJobType());
        job.setExperienceLevel(jobRequest.getExperienceLevel());
        job.setCompanyName(jobRequest.getCompanyName());
        
        Job updatedJob = jobRepository.save(job);
        return JobResponse.mapToJobResponse(updatedJob);
    }
    
    @Override
    public void deactivateJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setActive(false);
        jobRepository.save(job);
    }
    
    @Override
    public List<JobResponse> searchJobs(String title, String location, String jobType, String experienceLevel) {
        return jobRepository.searchJobs(title, location, jobType, experienceLevel).stream()
                .map(JobResponse::mapToJobResponse)
                .collect(Collectors.toList());
    }
}