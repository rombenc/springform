package com.yourstech.springform.repository;

import com.yourstech.springform.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Integer> {
    Optional<Form> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
