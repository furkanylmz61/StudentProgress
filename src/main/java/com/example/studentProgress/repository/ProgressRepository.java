package com.example.studentProgress.repository;

import com.example.studentProgress.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByStudentId(Long studentId);
    List<Progress> findByExamId(Long examId);
}
