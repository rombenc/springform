package com.yourstech.springform.repository;

import com.yourstech.springform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByFormId(Integer formId);

    List<Question> findByFormSlug(String formSlug);
}
