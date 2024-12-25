package com.example.studentProgress.repository;

import com.example.studentProgress.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByEmail(String email);
    Optional<Teacher> findByUserId(Long userId);


}
