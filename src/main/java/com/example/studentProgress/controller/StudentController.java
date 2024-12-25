package com.example.studentProgress.controller;

import com.example.studentProgress.dto.student.StudentRequestDto;
import com.example.studentProgress.dto.student.StudentResponseDto;
import com.example.studentProgress.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Öğrenci oluşturma
    @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto request) {
        StudentResponseDto dto = studentService.createStudent(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Tüm öğrencileri listeleme
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // ID'ye göre öğrenci getirme
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        StudentResponseDto dto = studentService.getStudentById(id);
        return ResponseEntity.ok(dto);
    }

    // Öğrenci güncelleme
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDto request) {
        StudentResponseDto dto = studentService.updateStudent(id, request);
        return ResponseEntity.ok(dto);
    }

    // Öğrenci silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
