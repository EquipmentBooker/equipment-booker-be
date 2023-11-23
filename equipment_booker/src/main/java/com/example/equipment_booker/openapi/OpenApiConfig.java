package com.example.equipment_booker.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info())
                .components(new Components()
                        .securitySchemes(securitySchemes())
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Map<String, SecurityScheme> securitySchemes() {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        securitySchemes.put("bearerAuth", new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
        );
        return securitySchemes;
    }
}

