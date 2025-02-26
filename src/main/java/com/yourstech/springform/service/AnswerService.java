package com.yourstech.springform.service;

import com.yourstech.springform.model.Answer;
import com.yourstech.springform.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<Answer> saveAnswers(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }
}