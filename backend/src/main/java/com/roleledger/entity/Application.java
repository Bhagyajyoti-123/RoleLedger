package com.roleledger.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String role;
    private String status;
    private LocalDate dateApplied;
    private String notes;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public String getNotes() {
        return notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
