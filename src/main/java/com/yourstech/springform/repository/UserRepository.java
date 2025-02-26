package com.yourstech.springform.repository;

import com.yourstech.springform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByVerificationToken(String token);
}
