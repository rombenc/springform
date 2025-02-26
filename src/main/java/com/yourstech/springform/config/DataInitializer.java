package com.yourstech.springform.config;

import com.yourstech.springform.model.Role;
import com.yourstech.springform.model.User;
import com.yourstech.springform.repository.RoleRepository;
import com.yourstech.springform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(Role.builder().name("USER").createdDate(LocalDateTime.now()).build());
            }
        };
    }

    @Bean
    public CommandLineRunner createUser(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("USER role not found"));

            if (userRepository.findByEmail("user1@webtech.id").isEmpty()) {
                userRepository.save(User.builder()
                        .name("User 1")
                        .email("user1@webtech.id")
                        .password(passwordEncoder.encode("password1"))
                        .createdAt(LocalDateTime.now())
                        .isVerified(true)
                        .roles(List.of(userRole))
                        .build());
            }
            if (userRepository.findByEmail("user2@webtech.id").isEmpty()) {
                userRepository.save(User.builder()
                        .name("User 2")
                        .email("user2@webtech.id")
                        .password(passwordEncoder.encode("password2"))
                        .createdAt(LocalDateTime.now())
                        .isVerified(true)
                        .roles(List.of(userRole))
                        .build());
            }
            if (userRepository.findByEmail("user3@worldskills.org").isEmpty()) {
                userRepository.save(User.builder()
                        .name("User 3")
                        .email("user3@worldskills.org")
                        .password(passwordEncoder.encode("password3"))
                        .createdAt(LocalDateTime.now())
                        .isVerified(true)
                        .roles(List.of(userRole))
                        .build());
            }
        };
    }
}

