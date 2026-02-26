package com.roleledger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roleledger.dto.AIRequestDTO;
import com.roleledger.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiAIService {
    
    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.api.url}")
    private String apiUrl;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public GeminiAIService() {
        this.webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public String generateInterviewQuestions(String companyName, String role) {
        String prompt = String.format(
            "You are an expert technical interviewer and career coach. " +
            "Generate comprehensive interview questions for a %s position at %s. " +
            "Provide questions in the following categories:\n\n" +
            "1. TECHNICAL QUESTIONS (Java-specific):\n" +
            "   - Core Java concepts (OOP, Collections, Multithreading, Exception Handling)\n" +
            "   - Data Structures and Algorithms\n" +
            "   - System Design basics\n" +
            "   - Framework knowledge (Spring Boot if relevant)\n\n" +
            "2. BEHAVIORAL QUESTIONS:\n" +
            "   - STAR method scenarios\n" +
            "   - Teamwork and leadership\n" +
            "   - Problem-solving examples\n\n" +
            "3. ROLE-SPECIFIC QUESTIONS:\n" +
            "   - Questions tailored to %s role\n" +
            "   - Industry-specific knowledge\n\n" +
            "Format your response clearly with category headers. " +
            "Be specific and practical - avoid generic questions. " +
            "Include 5-7 questions per category. " +
            "Explain why each question is relevant for this role.",
            role, companyName, role
        );
        
        return callGeminiAPI(prompt);
    }
    
    public String generateNextStepAdvice(String companyName, String role, ApplicationStatus status) {
        String statusContext = getStatusContext(status);
        
        String prompt = String.format(
            "You are an AI career advisor specializing in placement and internship preparation. " +
            "A candidate has applied for a %s position at %s and their current status is: %s.\n\n" +
            "Provide detailed, actionable preparation advice:\n\n" +
            "1. IMMEDIATE NEXT STEPS:\n" +
            "   - What should they focus on RIGHT NOW?\n" +
            "   - Specific skills to brush up on\n" +
            "   - Resources to use\n\n" +
            "2. PREPARATION STRATEGY:\n" +
            "   - Technical preparation (%s)\n" +
            "   - Behavioral preparation\n" +
            "   - Company research\n\n" +
            "3. TIMELINE & PRIORITY:\n" +
            "   - What to do in the next 24 hours\n" +
            "   - What to do in the next week\n" +
            "   - Long-term preparation\n\n" +
            "4. COMMON PITFALLS TO AVOID:\n" +
            "   - Mistakes candidates make at this stage\n" +
            "   - How to stand out\n\n" +
            "Be specific, practical, and tailored to the %s role. " +
            "Explain WHY each recommendation is important. " +
            "Context: %s",
            role, companyName, status, role, role, statusContext
        );
        
        return callGeminiAPI(prompt);
    }
    
    public String generateResumeFeedback(String resumeText, String targetRole) {
        String prompt = String.format(
            "You are an expert resume reviewer and career coach. " +
            "Analyze the following resume and provide feedback specifically for a %s position:\n\n" +
            "RESUME:\n%s\n\n" +
            "Provide feedback in the following structure:\n\n" +
            "1. STRENGTHS:\n" +
            "   - What stands out positively\n" +
            "   - Relevant skills and experiences\n" +
            "   - Strong points for %s role\n\n" +
            "2. GAPS & IMPROVEMENTS:\n" +
            "   - Missing skills or experiences\n" +
            "   - Areas that need strengthening\n" +
            "   - Specific recommendations\n\n" +
            "3. KEYWORDS & OPTIMIZATION:\n" +
            "   - Important keywords to include\n" +
            "   - ATS optimization suggestions\n" +
            "   - Role-specific terminology\n\n" +
            "4. ACTIONABLE RECOMMENDATIONS:\n" +
            "   - Specific changes to make\n" +
            "   - Skills to highlight\n" +
            "   - Formatting suggestions\n\n" +
            "Be constructive, specific, and focused on helping the candidate succeed in %s applications. " +
            "Provide concrete examples and actionable advice.",
            targetRole, resumeText, targetRole, targetRole
        );
        
        return callGeminiAPI(prompt);
    }
    
    private String getStatusContext(ApplicationStatus status) {
        Map<ApplicationStatus, String> contextMap = new HashMap<>();
        contextMap.put(ApplicationStatus.APPLIED, 
            "Just applied - waiting for initial screening");
        contextMap.put(ApplicationStatus.ONLINE_ASSESSMENT, 
            "Online assessment stage - need to prepare for coding challenges and technical tests");
        contextMap.put(ApplicationStatus.INTERVIEW, 
            "Interview stage - need to prepare for technical and behavioral interviews");
        contextMap.put(ApplicationStatus.OFFER, 
            "Received offer - congratulations! Consider negotiation and decision-making");
        contextMap.put(ApplicationStatus.REJECTED, 
            "Application was rejected - learn from experience and improve for next time");
        
        return contextMap.getOrDefault(status, "Unknown status");
    }
    
    private String callGeminiAPI(String prompt) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> contents = new HashMap<>();
            Map<String, Object> part = new HashMap<>();
            part.put("text", prompt);
            
            contents.put("parts", new Object[]{part});
            requestBody.put("contents", new Object[]{contents});
            
            String url = apiUrl + "?key=" + apiKey;
            
            String response = webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            // Parse response
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode candidates = jsonNode.get("candidates");
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).get("content");
                if (content != null) {
                    JsonNode parts = content.get("parts");
                    if (parts != null && parts.isArray() && parts.size() > 0) {
                        JsonNode text = parts.get(0).get("text");
                        if (text != null) {
                            return text.asText();
                        }
                    }
                }
            }
            
            return "Error: Could not parse AI response";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating AI response: " + e.getMessage();
        }
    }
}
