package com.example.campuslostfound.controller;

import com.example.campuslostfound.model.User;
import com.example.campuslostfound.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody Map<String, String> signupData) {
    Map<String, String> response = new HashMap<>();
    
    try {
        User user = new User();
        user.setFullName(signupData.get("name"));  // React sends "name"
        user.setEmail(signupData.get("email"));
        user.setPassword(signupData.get("password"));
        
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("error", "Email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        User savedUser = userRepository.save(user);
        
        response.put("message", "User created successfully");
        response.put("userId", savedUser.getId().toString());
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        response.put("error", "Signup failed: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
    
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
    Map<String, Object> response = new HashMap<>();
    
    try {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // For now, compare plain passwords (in production, use BCrypt)
        if (!user.getPassword().equals(password)) {
            response.put("message", "Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Create user object for frontend
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getFullName());
        userData.put("email", user.getEmail());
        
        // Create a simple token (in production, use JWT)
        String token = "jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
        
        response.put("token", token);
        response.put("user", userData);
        response.put("message", "Login successful");
        
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        response.put("message", "Login failed: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
}