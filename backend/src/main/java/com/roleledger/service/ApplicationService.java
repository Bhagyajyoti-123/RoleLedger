package com.roleledger.service;

import com.roleledger.entity.Application;
import com.roleledger.entity.User;
import com.roleledger.repository.ApplicationRepository;
import com.roleledger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    // Get current logged-in user
    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Save new application
    public Application saveApplication(Application application) {

        User currentUser = getCurrentUser();
        application.setUser(currentUser);

        return applicationRepository.save(application);
    }

    // Get applications of logged-in user
    public List<Application> getMyApplications() {

        User currentUser = getCurrentUser();
        return applicationRepository.findByUser(currentUser);
    }

    // Get all applications (for admin if needed)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}