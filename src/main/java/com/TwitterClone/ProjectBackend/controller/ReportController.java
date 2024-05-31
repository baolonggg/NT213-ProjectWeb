package com.TwitterClone.ProjectBackend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

    @PostMapping("/report")
    public void receiveViolationReport(@RequestBody String violationReport) {
        // Log the violation report
        System.out.println("Received violation report:\n" + violationReport);
        // You can perform additional processing or logging here
    }

    @GetMapping("/testt/{message}")
    public String xss(@PathVariable String message) {
        message = validateAndSanitizeInput(message);
        return message;
    }

    private String validateAndSanitizeInput(String input) {
        // Perform input validation and sanitation based on your requirements
        // For simplicity, we'll use HTML encoding as an example
        return org.owasp.encoder.Encode.forHtml(input);
    }
}