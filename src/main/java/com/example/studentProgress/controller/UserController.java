package com.example.studentProgress.controller;

import com.example.studentProgress.dto.user.UserDetailResponseDto;
import com.example.studentProgress.dto.user.UserRequestDto;
import com.example.studentProgress.dto.user.UserResponseDto;
import com.example.studentProgress.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{userId}/details")
    public ResponseEntity<UserDetailResponseDto> getUserDetailsByRole(@PathVariable Long userId) {
        UserDetailResponseDto userDetails = userService.getUserDetailsByRole(userId);
        return ResponseEntity.ok(userDetails); // 200 OK
    }


    //kullanıcı ekleme
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Valid  @RequestBody UserRequestDto request){
        UserResponseDto dto = userService.createUser(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED); // 201 kod döndür = created
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // 200 ok dönüyoruz
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){
        UserResponseDto dto = userService.GetUserById(userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto request){
        UserResponseDto dto = userService.updateUser(userId, request);
        return ResponseEntity.ok(dto); //200 ok dön
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId
    ) {
        userService.DeleteUserById(userId);
        return ResponseEntity.noContent().build(); // 204 No Content dönüyoruz
    }


}
