package com.roleledger.dto;

public class AuthResponse {

    private String token;
    private String type;
    private Long id;
    private String name;
    private String email;

    public AuthResponse(String token, String type, Long id, String name, String email) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
