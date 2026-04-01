package com.roleledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class RoleLedgerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoleLedgerApplication.class, args);
    }
}