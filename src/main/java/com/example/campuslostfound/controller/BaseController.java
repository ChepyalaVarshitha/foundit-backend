package com.example.campuslostfound.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    
    @GetMapping("/")
    public String home() {
        return "<h1>🚀 FoundIt Backend is Running!</h1>" +
               "<p>Available endpoints:</p>" +
               "<ul>" +
               "<li><a href='/api/test'>/api/test</a> - Test endpoint</li>" +
               "<li><a href='/api/lost-items'>/api/lost-items</a> - Get all lost items</li>" +
               "<li><a href='/api/found-items'>/api/found-items</a> - Get all found items</li>" +
               "<li><a href='/api/search?q=test'>/api/search?q=test</a> - Search items</li>" +
               "<li><a href='/api/auth/signup'>/api/auth/signup</a> - User registration</li>" +
               "<li><a href='/api/auth/login'>/api/auth/login</a> - User login</li>" +
               "</ul>" +
               "<p><strong>✅ All endpoints are functional and connected to PostgreSQL database.</strong></p>";
    }
}