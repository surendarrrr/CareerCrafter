package com.careercrafter.repository;

import com.careercrafter.model.Resume;
import com.careercrafter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
    Optional<Resume> findByUserAndIsActiveTrue(User user);
}