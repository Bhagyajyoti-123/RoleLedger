package com.roleledger.controller;

import com.roleledger.dto.AIRequestDTO;
import com.roleledger.enums.ApplicationStatus;
import com.roleledger.service.ApplicationService;
import com.roleledger.service.GeminiAIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {
    
    @Autowired
    private GeminiAIService geminiAIService;
    
    @PostMapping("/interview-questions")
    public ResponseEntity<?> generateInterviewQuestions(@Valid @RequestBody AIRequestDTO request) {
        try {
            String questions = geminiAIService.generateInterviewQuestions(
                request.getCompanyName(),
                request.getRole()
            );
            return ResponseEntity.ok(Map.of("response", questions));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to generate interview questions: " + e.getMessage()));
        }
    }
    
    @PostMapping("/next-step-advice")
    public ResponseEntity<?> generateNextStepAdvice(@Valid @RequestBody AIRequestDTO request) {
        try {
            ApplicationStatus status = ApplicationStatus.valueOf(request.getApplicationStatus());
            String advice = geminiAIService.generateNextStepAdvice(
                request.getCompanyName(),
                request.getRole(),
                status
            );
            return ResponseEntity.ok(Map.of("response", advice));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to generate advice: " + e.getMessage()));
        }
    }
    
    @PostMapping("/resume-feedback")
    public ResponseEntity<?> generateResumeFeedback(@Valid @RequestBody AIRequestDTO request) {
        try {
            String feedback = geminiAIService.generateResumeFeedback(
                request.getInput(),
                request.getRole()
            );
            return ResponseEntity.ok(Map.of("response", feedback));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to generate resume feedback: " + e.getMessage()));
        }
    }
}
