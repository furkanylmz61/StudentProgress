package com.example.studentProgress.repository;

import com.example.studentProgress.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Optional<Student> findByUserId(Long userId);

}
