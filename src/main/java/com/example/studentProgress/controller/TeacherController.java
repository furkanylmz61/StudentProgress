package com.example.studentProgress.controller;


import com.example.studentProgress.dto.teacher.TeacherRequestDto;
import com.example.studentProgress.dto.teacher.TeacherResponseDto;
import com.example.studentProgress.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // Öğretmen oluşturma
    @PostMapping("/create")
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto request) {
        TeacherResponseDto dto = teacherService.createTeacher(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED); // 201 Created
    }

    // Tüm öğretmenleri listeleme
    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        List<TeacherResponseDto> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers); // 200 OK
    }

    // ID'ye göre öğretmen getirme
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        TeacherResponseDto dto = teacherService.getTeacherById(id);
        return ResponseEntity.ok(dto); // 200 OK
    }

    // Öğretmen güncelleme
    @PutMapping("/update/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherRequestDto request) {
        TeacherResponseDto dto = teacherService.updateTeacher(id, request);
        return ResponseEntity.ok(dto); // 200 OK
    }

    // Öğretmen silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

