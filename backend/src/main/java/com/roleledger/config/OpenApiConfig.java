package com.roleledger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI roleLedgerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RoleLedger API")
                        .description("RoleLedger - Role Based Access Control System using Spring Boot & JWT")
                        .version("1.0"));
    }
}