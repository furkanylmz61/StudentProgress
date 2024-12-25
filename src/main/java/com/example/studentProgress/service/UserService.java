package com.example.studentProgress.service;


import com.example.studentProgress.dto.student.StudentResponseDto;
import com.example.studentProgress.dto.teacher.TeacherResponseDto;
import com.example.studentProgress.dto.user.UserDetailResponseDto;
import com.example.studentProgress.dto.user.UserRequestDto;
import com.example.studentProgress.dto.user.UserResponseDto;
import com.example.studentProgress.model.Student;
import com.example.studentProgress.model.Teacher;
import com.example.studentProgress.model.User;
import com.example.studentProgress.repository.StudentRepository;
import com.example.studentProgress.repository.TeacherRepository;
import com.example.studentProgress.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public UserService(UserRepository userRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    //Bütün kullancıları listeleme
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()// veriler üzerinde sıralı bir şekilde işlem yapmamızı sağlar List<User> -> Stream<User>
                .map(this::mapToResponseDto)// stream üzerindeki her elemanı alır ve bunlar içinde işlem yapar Stream<User> -> Stream<UserResponseDto>
                .collect(Collectors.toList());// bir flowu farklı bir veri yapısına dönüştürmek için kullanılır  Stream<UserResponseDto> -> List<UserResponseDto>
    }



    //Kullanıcı oluşturma
    public UserResponseDto createUser(UserRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // requestDto -> Model dönüşümü
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        //model -> response dönüşümü
        return mapToResponseDto(savedUser);
    }

    //Id ye göre kullanıcı listeleme
    public UserResponseDto GetUserById(Long userId) {
        User user  = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        return mapToResponseDto(user);
    }


    //update işlemi yapılıyor
    public UserResponseDto updateUser(Long userId, UserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found")); // user sorgulaması yapılıyor burada

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return mapToResponseDto(savedUser);
    }


    //user id alınarak delete işlemi yapılıyor.
    public void DeleteUserById(Long userId) {
        User user  = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        userRepository.delete(user);
    }

    // Kullanıcının rolüne göre Teacher veya Student bilgilerini listeleme
    public UserDetailResponseDto getUserDetailsByRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        UserDetailResponseDto responseDto = new UserDetailResponseDto();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setRole(user.getRole());
        responseDto.setCreatedAt(user.getCreatedAt().toString());

        if ("TEACHER".equalsIgnoreCase(user.getRole())) {
            Optional<Teacher> teacher = teacherRepository.findByUserId(userId);
            teacher.ifPresent(t -> responseDto.setTeacher(mapToTeacherResponseDto(t)));
        } else if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            Optional<Student> student = studentRepository.findByUserId(userId);
            student.ifPresent(s -> responseDto.setStudent(mapToStudentResponseDto(s)));
        }

        return responseDto;
    }



    // Teacher -> TeacherResponseDto dönüşümü
    private TeacherResponseDto mapToTeacherResponseDto(Teacher teacher) {
        TeacherResponseDto responseDto = new TeacherResponseDto();
        responseDto.setId(teacher.getId());
        responseDto.setFirstName(teacher.getFirstName());
        responseDto.setLastName(teacher.getLastName());
        responseDto.setEmail(teacher.getEmail());
        responseDto.setPhoneNumber(teacher.getPhoneNumber());
        responseDto.setBranch(teacher.getBranch());
        return responseDto;
    }

    // Student -> StudentResponseDto dönüşümü
    private StudentResponseDto mapToStudentResponseDto(Student student) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setEmail(student.getEmail());
        responseDto.setPhoneNumber(student.getPhoneNumber());
        responseDto.setGradeLevel(student.getGradeLevel());
        return responseDto;
    }



    //modelden response 'a dönüşüm metodu
    public UserResponseDto mapToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUsername(user.getUsername());
        responseDto.setId(user.getId());
        responseDto.setRole(user.getRole());
        responseDto.setCreatedAt(user.getCreatedAt().toString());

        return responseDto;
    }
}
