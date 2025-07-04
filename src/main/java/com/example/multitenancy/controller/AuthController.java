package com.example.multitenancy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.multitenancy.dto.AuthResponse;
import com.example.multitenancy.dto.LoginRequest;
import com.example.multitenancy.model.Employee;
import com.example.multitenancy.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Employee employee = authService.authenticateUser(request.getClientCode(), request.getEmail(), request.getPassword());
        if (employee != null) {
            return ResponseEntity.ok(new AuthResponse(request.getClientCode(), employee.getEmployeeCode(), "Login successful"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logoutUser();
        return ResponseEntity.ok("Logout successful");
    }
}
