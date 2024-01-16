package com.AlTaraf.Booking.controller;

import com.AlTaraf.Booking.dto.UserRegisterDto;
import com.AlTaraf.Booking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // Constructor
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        try {
            userService.registerUser(userRegisterDto);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRegisterDto userRegisterDto) {
        try {
            userService.updateUser(id, userRegisterDto);
            return ResponseEntity.ok("User updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRegisterDto> getUserById(@PathVariable Long id) {
        UserRegisterDto userDto = userService.getUserById(id);
        return userDto != null
                ? ResponseEntity.ok(userDto)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRegisterDto>> getAllUsers() {
        List<UserRegisterDto> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}