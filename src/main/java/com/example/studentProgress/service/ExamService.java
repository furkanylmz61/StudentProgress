package com.example.studentProgress.service;

import com.example.studentProgress.dto.exam.ExamRequestDto;
import com.example.studentProgress.dto.exam.ExamResponseDto;
import com.example.studentProgress.model.Exam;
import com.example.studentProgress.model.Teacher;
import com.example.studentProgress.repository.ExamRepository;
import com.example.studentProgress.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final TeacherRepository teacherRepository;

    public ExamService(ExamRepository examRepository, TeacherRepository teacherRepository) {
        this.examRepository = examRepository;
        this.teacherRepository = teacherRepository;
    }

    // Sınav oluşturma
    public ExamResponseDto createExam(ExamRequestDto request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + request.getTeacherId()));

        Exam exam = new Exam();
        exam.setName(request.getName());
        exam.setDuration(request.getDuration());
        exam.setTeacher(teacher);

        Exam savedExam = examRepository.save(exam);
        return mapToResponseDto(savedExam);
    }

    // Tüm sınavları listeleme
    public List<ExamResponseDto> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ID'ye göre sınav getirme
    public ExamResponseDto getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));
        return mapToResponseDto(exam);
    }

    // Öğretmene göre sınavları listeleme
    public List<ExamResponseDto> getExamsByTeacherId(Long teacherId) {
        List<Exam> exams = examRepository.findByTeacherId(teacherId);
        return exams.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Sınav güncelleme
    public ExamResponseDto updateExam(Long id, ExamRequestDto request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + request.getTeacherId()));

        exam.setName(request.getName());
        exam.setDuration(request.getDuration());
        exam.setTeacher(teacher);

        Exam updatedExam = examRepository.save(exam);
        return mapToResponseDto(updatedExam);
    }

    // Sınav silme
    public void deleteExam(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));
        examRepository.delete(exam);
    }

    // Model -> DTO dönüşüm metodu
    private ExamResponseDto mapToResponseDto(Exam exam) {
        ExamResponseDto responseDto = new ExamResponseDto();
        responseDto.setId(exam.getId());
        responseDto.setName(exam.getName());
        responseDto.setDuration(exam.getDuration());
        responseDto.setTeacherName(exam.getTeacher().getFirstName() + " " + exam.getTeacher().getLastName());
        return responseDto;
    }
}

