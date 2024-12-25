package com.example.studentProgress.repository;

import com.example.studentProgress.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username); // Kullanıcı adı var mı diye kontrol eder
}
