package com.example.studentProgress.repository;

import com.example.studentProgress.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByTeacherId(Long teacherId);
}
