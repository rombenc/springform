package com.yourstech.springform.repository;

import com.yourstech.springform.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findBySubmitId(Integer submitId);
}
