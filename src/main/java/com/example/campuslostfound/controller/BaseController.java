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
               "<li><a href='/api/h2-console'>/api/h2-console</a> - Database console</li>" +
               "<li>/api/lost-items - Coming soon</li>" +
               "<li>/api/found-items - Coming soon</li>" +
               "</ul>";
    }
}