package com.careercrafter.controller;

import com.careercrafter.dto.JobRequest;
import com.careercrafter.dto.JobResponse;
import com.careercrafter.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    
    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<JobResponse> postJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.ok(jobService.postJob(jobRequest));
    }
    
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }
    
    @GetMapping("/employer/{employerId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<List<JobResponse>> getJobsByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobService.getJobsByEmployer(employerId));
    }
    
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }
    
    @PutMapping("/{jobId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long jobId,
            @Valid @RequestBody JobRequest jobRequest
    ) {
        return ResponseEntity.ok(jobService.updateJob(jobId, jobRequest));
    }
    
    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasAuthority('EMPLOYER')")
    public ResponseEntity<String> deactivateJob(@PathVariable Long jobId) {
        jobService.deactivateJob(jobId);
        return ResponseEntity.ok("Job deactivated successfully");
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String experienceLevel
    ) {
        return ResponseEntity.ok(jobService.searchJobs(title, location, jobType, experienceLevel));
    }
}