package com.careercrafter.controller;

import com.careercrafter.dto.ResumeRequest;
import com.careercrafter.dto.ResumeResponse;
import com.careercrafter.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    
    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<ResumeResponse> uploadResume(
            @Valid @ModelAttribute ResumeRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(resumeService.uploadResume(request, file));
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('JOB_SEEKER', 'EMPLOYER')")
    public ResponseEntity<List<ResumeResponse>> getResumesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resumeService.getResumesByUser(userId));
    }
    
    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasAnyAuthority('JOB_SEEKER', 'EMPLOYER')")
    public ResponseEntity<ResumeResponse> getActiveResumeByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resumeService.getActiveResumeByUser(userId));
    }
    
    @PutMapping("/{resumeId}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<ResumeResponse> updateResume(
            @PathVariable Long resumeId,
            @Valid @ModelAttribute ResumeRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(resumeService.updateResume(resumeId, request, file));
    }
    
    @DeleteMapping("/{resumeId}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<String> deactivateResume(@PathVariable Long resumeId) {
        resumeService.deactivateResume(resumeId);
        return ResponseEntity.ok("Resume deactivated successfully");
    }
}