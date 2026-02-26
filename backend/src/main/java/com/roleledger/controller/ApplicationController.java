package com.roleledger.controller;

import com.roleledger.dto.ApplicationDTO;
import com.roleledger.dto.DashboardStatsDTO;
import com.roleledger.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        try {
            ApplicationDTO application = applicationService.getApplicationById(id);
            return ResponseEntity.ok(application);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO created = applicationService.createApplication(applicationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable Long id, 
                                               @Valid @RequestBody ApplicationDTO applicationDTO) {
        try {
            ApplicationDTO updated = applicationService.updateApplication(id, applicationDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok(Map.of("message", "Application deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = applicationService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}
