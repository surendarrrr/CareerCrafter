package com.careercrafter.service.impl;

import com.careercrafter.dto.ResumeRequest;
import com.careercrafter.dto.ResumeResponse;
import com.careercrafter.model.Resume;
import com.careercrafter.model.User;
import com.careercrafter.repository.ResumeRepository;
import com.careercrafter.repository.UserRepository;
import com.careercrafter.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    
    @Value("${app.file.upload-dir}")
    private String uploadDir;
    
    @Override
    public ResumeResponse uploadResume(ResumeRequest resumeRequest, MultipartFile file) {
        User user = userRepository.findById(resumeRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Deactivate any existing active resume for this user
        resumeRepository.findByUserAndIsActiveTrue(user).ifPresent(resume -> {
            resume.setActive(false);
            resumeRepository.save(resume);
        });
        
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);
            
            // Save file to disk
            Files.copy(file.getInputStream(), filePath);
            
            // Create and save resume record
            Resume resume = Resume.builder()
                    .user(user)
                    .fileName(originalFilename)
                    .filePath(filePath.toString())
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .skills(resumeRequest.getSkills())
                    .experience(resumeRequest.getExperience())
                    .education(resumeRequest.getEducation())
                    .build();
            
            Resume savedResume = resumeRepository.save(resume);
            return ResumeResponse.mapToResumeResponse(savedResume);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume", e);
        }
    }
    
    @Override
    public List<ResumeResponse> getResumesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return resumeRepository.findByUser(user).stream()
                .map(ResumeResponse::mapToResumeResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public ResumeResponse getActiveResumeByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Resume resume = resumeRepository.findByUserAndIsActiveTrue(user)
                .orElseThrow(() -> new RuntimeException("No active resume found"));
        
        return ResumeResponse.mapToResumeResponse(resume);
    }
    
    @Override
    public ResumeResponse updateResume(Long resumeId, ResumeRequest resumeRequest, MultipartFile file) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        
        try {
            // Delete old file if a new one is provided
            if (file != null && !file.isEmpty()) {
                Files.deleteIfExists(Paths.get(resume.getFilePath()));
                
                // Save new file
                Path uploadPath = Paths.get(uploadDir);
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                Path filePath = uploadPath.resolve(uniqueFilename);
                
                Files.copy(file.getInputStream(), filePath);
                
                resume.setFileName(originalFilename);
                resume.setFilePath(filePath.toString());
                resume.setFileSize(file.getSize());
                resume.setContentType(file.getContentType());
            }
            
            // Update other fields
            resume.setSkills(resumeRequest.getSkills());
            resume.setExperience(resumeRequest.getExperience());
            resume.setEducation(resumeRequest.getEducation());
            
            Resume updatedResume = resumeRepository.save(resume);
            return ResumeResponse.mapToResumeResponse(updatedResume);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update resume", e);
        }
    }
    
    @Override
    public void deactivateResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        resume.setActive(false);
        resumeRepository.save(resume);
    }
}