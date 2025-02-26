package com.yourstech.springform.config;

import com.yourstech.springform.model.Role;
import com.yourstech.springform.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    // todo : jangan lupa initialize data buat user

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(Role.builder().name("USER").createdDate(LocalDateTime.now()).build());
            }
        };
    }
}
