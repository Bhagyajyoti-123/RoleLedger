package com.roleledger.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String userProfile() {
        return "Welcome User";
    }
}