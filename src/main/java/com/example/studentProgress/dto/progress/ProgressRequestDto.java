package com.example.studentProgress.dto.progress;

import jakarta.validation.constraints.NotNull;

public class ProgressRequestDto {

    @NotNull(message = "Score cannot be null")
    private Double score;

    @NotNull(message = "Status cannot be null")
    private String status; // Status ENUM: PASSED, FAILED, IN_PROGRESS

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Exam ID cannot be null")
    private Long examId;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }
}
