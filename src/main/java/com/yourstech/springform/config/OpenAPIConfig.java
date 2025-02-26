package com.yourstech.springform.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SPRING FORM - APP",
                version = "0.1",
                description = "Form Management API",
                contact = @Contact(
                        name = "dimasnurfaouzi@gmail.com"
                )
        )
)
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")));
    }
}