package com.example.studentProgress.controller;

import com.example.studentProgress.dto.progress.ProgressRequestDto;
import com.example.studentProgress.dto.progress.ProgressResponseDto;
import com.example.studentProgress.service.ProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // Progress oluşturma
    @PostMapping("/create")
    public ResponseEntity<ProgressResponseDto> createProgress(@RequestBody ProgressRequestDto request) {
        ProgressResponseDto dto = progressService.createProgress(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Tüm progress kayıtlarını listeleme
    @GetMapping
    public ResponseEntity<List<ProgressResponseDto>> getAllProgress() {
        List<ProgressResponseDto> progressList = progressService.getAllProgress();
        return ResponseEntity.ok(progressList);
    }

    // ID'ye göre progress getirme
    @GetMapping("/{id}")
    public ResponseEntity<ProgressResponseDto> getProgressById(@PathVariable Long id) {
        ProgressResponseDto dto = progressService.getProgressById(id);
        return ResponseEntity.ok(dto);
    }

    // Öğrenciye göre progress listeleme
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ProgressResponseDto>> getProgressByStudentId(@PathVariable Long studentId) {
        List<ProgressResponseDto> progressList = progressService.getProgressByStudentId(studentId);
        return ResponseEntity.ok(progressList);
    }

    // Sınava göre progress listeleme
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ProgressResponseDto>> getProgressByExamId(@PathVariable Long examId) {
        List<ProgressResponseDto> progressList = progressService.getProgressByExamId(examId);
        return ResponseEntity.ok(progressList);
    }

    // Progress güncelleme
    @PutMapping("/update/{id}")
    public ResponseEntity<ProgressResponseDto> updateProgress(@PathVariable Long id, @RequestBody ProgressRequestDto request) {
        ProgressResponseDto dto = progressService.updateProgress(id, request);
        return ResponseEntity.ok(dto);
    }

    // Progress silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
