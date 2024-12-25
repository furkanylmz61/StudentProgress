package com.example.studentProgress.service;

import com.example.studentProgress.dto.teacher.TeacherRequestDto;
import com.example.studentProgress.dto.teacher.TeacherResponseDto;
import com.example.studentProgress.model.Teacher;
import com.example.studentProgress.model.User;
import com.example.studentProgress.repository.TeacherRepository;
import com.example.studentProgress.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }


    // Tüm öğretmenleri listeleme
    public List<TeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ID'ye göre öğretmen bulma
    public TeacherResponseDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));
        return mapToResponseDto(teacher);
    }
    // Öğretmen silme
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));
        teacherRepository.delete(teacher);
    }

    // Öğretmen güncelleme
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + id));

        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setBranch(request.getBranch());

        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponseDto(updatedTeacher);
    }

    // Öğretmen oluşturma
    public TeacherResponseDto createTeacher(TeacherRequestDto request) {
        // User doğrulaması
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        if (!"TEACHER".equals(user.getRole())) {
            throw new IllegalArgumentException("User role must be TEACHER");
        }

        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Teacher oluşturma
        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmail(request.getEmail());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setBranch(request.getBranch());
        teacher.setUser(user); // User ile ilişkilendirme

        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapToResponseDto(savedTeacher);
    }

    // Model -> DTO dönüşüm metodu
    private TeacherResponseDto mapToResponseDto(Teacher teacher) {
        TeacherResponseDto responseDto = new TeacherResponseDto();
        responseDto.setId(teacher.getId());
        responseDto.setFirstName(teacher.getFirstName());
        responseDto.setLastName(teacher.getLastName());
        responseDto.setEmail(teacher.getEmail());
        responseDto.setPhoneNumber(teacher.getPhoneNumber());
        responseDto.setBranch(teacher.getBranch());
        return responseDto;
    }


}
