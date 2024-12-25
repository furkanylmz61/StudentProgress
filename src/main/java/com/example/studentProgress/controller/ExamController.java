package com.example.studentProgress.controller;


import com.example.studentProgress.dto.exam.ExamRequestDto;
import com.example.studentProgress.dto.exam.ExamResponseDto;
import com.example.studentProgress.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // Sınav oluşturma
    @PostMapping("/create")
    public ResponseEntity<ExamResponseDto> createExam(@RequestBody ExamRequestDto request) {
        ExamResponseDto dto = examService.createExam(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Tüm sınavları listeleme
    @GetMapping
    public ResponseEntity<List<ExamResponseDto>> getAllExams() {
        List<ExamResponseDto> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    // ID'ye göre sınav getirme
    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDto> getExamById(@PathVariable Long id) {
        ExamResponseDto dto = examService.getExamById(id);
        return ResponseEntity.ok(dto);
    }

    // Öğretmene göre sınavları listeleme
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ExamResponseDto>> getExamsByTeacherId(@PathVariable Long teacherId) {
        List<ExamResponseDto> exams = examService.getExamsByTeacherId(teacherId);
        return ResponseEntity.ok(exams);
    }

    // Sınav güncelleme
    @PutMapping("/update/{id}")
    public ResponseEntity<ExamResponseDto> updateExam(@PathVariable Long id, @RequestBody ExamRequestDto request) {
        ExamResponseDto dto = examService.updateExam(id, request);
        return ResponseEntity.ok(dto);
    }

    // Sınav silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}

