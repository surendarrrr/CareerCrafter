package com.careercrafter.repository;

import com.careercrafter.model.Job;
import com.careercrafter.model.JobApplication;
import com.careercrafter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobSeeker(User jobSeeker);
    List<JobApplication> findByJob(Job job);
    Optional<JobApplication> findByJobAndJobSeeker(Job job, User jobSeeker);
    boolean existsByJobAndJobSeeker(Job job, User jobSeeker);
}