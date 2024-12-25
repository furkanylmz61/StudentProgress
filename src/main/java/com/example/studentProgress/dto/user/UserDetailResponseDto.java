package com.example.studentProgress.dto.user;

import com.example.studentProgress.dto.student.StudentResponseDto;
import com.example.studentProgress.dto.teacher.TeacherResponseDto;


public class UserDetailResponseDto {
    private Long id;
    private String username;
    private String role;
    private String createdAt;
    private TeacherResponseDto teacher; // Eğer kullanıcı TEACHER ise
    private StudentResponseDto student; // Eğer kullanıcı STUDENT ise

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public TeacherResponseDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponseDto teacher) {
        this.teacher = teacher;
    }

    public StudentResponseDto getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDto student) {
        this.student = student;
    }
}
