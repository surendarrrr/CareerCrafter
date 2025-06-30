package com.careercrafter.repository;

import com.careercrafter.model.Job;
import com.careercrafter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEmployer(User employer);
    List<Job> findByIsActiveTrue();
    
    @Query("SELECT j FROM Job j WHERE " +
           "(:title IS NULL OR j.title LIKE %:title%) AND " +
           "(:location IS NULL OR j.location LIKE %:location%) AND " +
           "(:jobType IS NULL OR j.jobType = :jobType) AND " +
           "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) AND " +
           "j.isActive = true")
    List<Job> searchJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("jobType") String jobType,
            @Param("experienceLevel") String experienceLevel);
}