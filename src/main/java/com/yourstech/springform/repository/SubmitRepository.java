package com.yourstech.springform.repository;

import com.yourstech.springform.model.Submit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Integer> {
    List<Submit> findByFormSlug(String formSlug);
}
