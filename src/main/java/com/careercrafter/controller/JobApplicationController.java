package com.careercrafter.controller;

import com.careercrafter.dto.JobApplicationRequest;
import com.careercrafter.dto.JobResponse;
import com.careercrafter.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    
    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<JobResponse> applyForJob(@Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(jobApplicationService.applyForJob(request));
    }
    
    @GetMapping("/job-seeker/{jobSeekerId}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<List<JobResponse>> getApplicationsByJobSeeker(@PathVariable Long jobSeekerId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJobSeeker(jobSeekerId));
    }
    
    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<List<JobResponse>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJob(jobId));
    }
    
    @DeleteMapping("/{applicationId}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<String> withdrawApplication(@PathVariable Long applicationId) {
        jobApplicationService.withdrawApplication(applicationId);
        return ResponseEntity.ok("Application withdrawn successfully");
    }
    
    @PatchMapping("/{applicationId}/status")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status
    ) {
        jobApplicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok("Application status updated successfully");
    }
}