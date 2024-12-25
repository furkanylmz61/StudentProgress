package com.example.studentProgress.service;

import com.example.studentProgress.dto.progress.ProgressRequestDto;
import com.example.studentProgress.dto.progress.ProgressResponseDto;
import com.example.studentProgress.model.Exam;
import com.example.studentProgress.model.Progress;
import com.example.studentProgress.model.Student;
import com.example.studentProgress.model.Status;
import com.example.studentProgress.repository.ExamRepository;
import com.example.studentProgress.repository.ProgressRepository;
import com.example.studentProgress.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public ProgressService(ProgressRepository progressRepository, StudentRepository studentRepository, ExamRepository examRepository) {
        this.progressRepository = progressRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    // Progress oluşturma
    public ProgressResponseDto createProgress(ProgressRequestDto request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + request.getStudentId()));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with ID: " + request.getExamId()));

        Progress progress = new Progress();
        progress.setScore(request.getScore());
        progress.setStatus(Status.valueOf(request.getStatus().toUpperCase())); // ENUM dönüşümü
        progress.setStudent(student);
        progress.setExam(exam);

        Progress savedProgress = progressRepository.save(progress);
        return mapToResponseDto(savedProgress);
    }

    // Tüm progress kayıtlarını listeleme
    public List<ProgressResponseDto> getAllProgress() {
        return progressRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ID'ye göre progress getirme
    public ProgressResponseDto getProgressById(Long id) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found with ID: " + id));
        return mapToResponseDto(progress);
    }

    // Öğrenciye göre progress listeleme
    public List<ProgressResponseDto> getProgressByStudentId(Long studentId) {
        List<Progress> progressList = progressRepository.findByStudentId(studentId);
        return progressList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Sınava göre progress listeleme
    public List<ProgressResponseDto> getProgressByExamId(Long examId) {
        List<Progress> progressList = progressRepository.findByExamId(examId);
        return progressList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Progress güncelleme
    public ProgressResponseDto updateProgress(Long id, ProgressRequestDto request) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found with ID: " + id));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + request.getStudentId()));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with ID: " + request.getExamId()));

        progress.setScore(request.getScore());
        progress.setStatus(Status.valueOf(request.getStatus().toUpperCase())); // ENUM dönüşümü
        progress.setStudent(student);
        progress.setExam(exam);

        Progress updatedProgress = progressRepository.save(progress);
        return mapToResponseDto(updatedProgress);
    }

    // Progress silme
    public void deleteProgress(Long id) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found with ID: " + id));
        progressRepository.delete(progress);
    }

    // Model -> DTO dönüşüm metodu
    private ProgressResponseDto mapToResponseDto(Progress progress) {
        ProgressResponseDto responseDto = new ProgressResponseDto();
        responseDto.setId(progress.getId());
        responseDto.setScore(progress.getScore());
        responseDto.setStatus(progress.getStatus().name());
        responseDto.setStudentName(progress.getStudent().getFirstName() + " " + progress.getStudent().getLastName());
        responseDto.setExamName(progress.getExam().getName());
        return responseDto;
    }
}
