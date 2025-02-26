package com.yourstech.springform.service;

import com.yourstech.springform.dto.request.QuestionRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.model.Question;
import jakarta.transaction.Transactional;

import java.util.List;

public interface QuestionService {
    @Transactional
    CommonResponse<QuestionResponse> createQuestion(String formSlug, QuestionRequest request);

    CommonResponse<List<QuestionResponse>> getAllQuestions(String formSlug);

    CommonResponse<QuestionResponse> getQuestionById(String formSlug, Integer questionId);

    @Transactional
    CommonResponse<QuestionResponse> updateQuestion(String formSlug, Integer questionId, QuestionRequest request);

    void removeQuestion(String formSlug, Integer questionId);
}
