package com.roleledger.service;

import com.roleledger.dto.ApplicationDTO;
import com.roleledger.dto.DashboardStatsDTO;
import com.roleledger.entity.Application;
import com.roleledger.entity.User;
import com.roleledger.enums.ApplicationStatus;
import com.roleledger.repository.ApplicationRepository;
import com.roleledger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    public List<ApplicationDTO> getAllApplications() {
        User user = getCurrentUser();
        return applicationRepository.findByUser(user)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ApplicationDTO getApplicationById(Long id) {
        User user = getCurrentUser();
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!app.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        return toDTO(app);
    }

    public ApplicationDTO createApplication(ApplicationDTO dto) {
        User user = getCurrentUser();
        Application app = toEntity(dto);
        app.setUser(user);
        return toDTO(applicationRepository.save(app));
    }

    public ApplicationDTO updateApplication(Long id, ApplicationDTO dto) {
        User user = getCurrentUser();
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!app.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        app.setCompanyName(dto.getCompanyName());
        app.setRole(dto.getRole());
        app.setStatus(dto.getStatus());
        app.setDateApplied(dto.getDateApplied());
        app.setNotes(dto.getNotes());
        return toDTO(applicationRepository.save(app));
    }

    public void deleteApplication(Long id) {
        User user = getCurrentUser();
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!app.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        applicationRepository.delete(app);
    }

    public DashboardStatsDTO getDashboardStats() {
        User user = getCurrentUser();
        long total = applicationRepository.countByUser(user);

        Map<String, Long> statusBreakdown = new LinkedHashMap<>();
        for (ApplicationStatus s : ApplicationStatus.values()) {
            statusBreakdown.put(s.name(), 0L);
        }

        List<Object[]> counts = applicationRepository.countByStatusAndUser(user);
        for (Object[] row : counts) {
            statusBreakdown.put((String) row[0], (Long) row[1]);
        }

        return new DashboardStatsDTO(total, statusBreakdown);
    }

    private ApplicationDTO toDTO(Application app) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(app.getId());
        dto.setCompanyName(app.getCompanyName());
        dto.setRole(app.getRole());
        dto.setStatus(app.getStatus());
        dto.setDateApplied(app.getDateApplied());
        dto.setNotes(app.getNotes());
        return dto;
    }

    private Application toEntity(ApplicationDTO dto) {
        Application app = new Application();
        app.setCompanyName(dto.getCompanyName());
        app.setRole(dto.getRole());
        app.setStatus(dto.getStatus() != null ? dto.getStatus() : "APPLIED");
        app.setDateApplied(dto.getDateApplied());
        app.setNotes(dto.getNotes());
        return app;
    }
}