package com.example.studentProgress.dto.exam;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ExamRequestDto {

    @NotBlank(message = "Exam name cannot be blank")
    private String name;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotNull(message = "Teacher ID cannot be null")
    private Long teacherId; // Exam ile ilişkilendirilecek Teacher ID

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
