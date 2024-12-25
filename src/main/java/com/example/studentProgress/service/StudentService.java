package com.example.studentProgress.service;

import com.example.studentProgress.dto.student.StudentRequestDto;
import com.example.studentProgress.dto.student.StudentResponseDto;
import com.example.studentProgress.model.Student;
import com.example.studentProgress.model.User;
import com.example.studentProgress.repository.StudentRepository;
import com.example.studentProgress.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    // Öğrenci oluşturma
    public StudentResponseDto createStudent(StudentRequestDto request) {
        // Kullanıcı doğrulaması
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        if (!"STUDENT".equals(user.getRole())) {
            throw new IllegalArgumentException("User role must be STUDENT");
        }

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Student oluşturma
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setGradeLevel(request.getGradeLevel());
        student.setUser(user); // User ile ilişkilendirme

        Student savedStudent = studentRepository.save(student);
        return mapToResponseDto(savedStudent);
    }

    // Tüm öğrencileri listeleme
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ID'ye göre öğrenci bulma
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        return mapToResponseDto(student);
    }

    // Öğrenci güncelleme
    public StudentResponseDto updateStudent(Long id, StudentRequestDto request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setGradeLevel(request.getGradeLevel());

        Student updatedStudent = studentRepository.save(student);
        return mapToResponseDto(updatedStudent);
    }

    // Öğrenci silme
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        studentRepository.delete(student);
    }

    // Model -> DTO dönüşüm metodu
    private StudentResponseDto mapToResponseDto(Student student) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setEmail(student.getEmail());
        responseDto.setPhoneNumber(student.getPhoneNumber());
        responseDto.setGradeLevel(student.getGradeLevel());
        return responseDto;
    }
}
