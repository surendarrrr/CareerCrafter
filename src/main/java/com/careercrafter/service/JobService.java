package com.careercrafter.service;

import com.careercrafter.dto.JobRequest;
import com.careercrafter.dto.JobResponse;

import java.util.List;

public interface JobService {
    JobResponse postJob(JobRequest jobRequest);
    List<JobResponse> getAllJobs();
    List<JobResponse> getJobsByEmployer(Long employerId);
    JobResponse getJobById(Long jobId);
    JobResponse updateJob(Long jobId, JobRequest jobRequest);
    void deactivateJob(Long jobId);
    List<JobResponse> searchJobs(String title, String location, String jobType, String experienceLevel);
}