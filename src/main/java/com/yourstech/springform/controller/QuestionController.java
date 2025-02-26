package com.yourstech.springform.controller;

import com.yourstech.springform.dto.request.QuestionRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forms/{formSlug}/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<CommonResponse<QuestionResponse>> createQuestion(
            @PathVariable String formSlug,
            @Valid @RequestBody QuestionRequest request) {
        CommonResponse<QuestionResponse> response = questionService.createQuestion(formSlug, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<QuestionResponse>>> getAllQuestions(@PathVariable String formSlug) {
        CommonResponse<List<QuestionResponse>> response = questionService.getAllQuestions(formSlug);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<CommonResponse<QuestionResponse>> getQuestionById(
            @PathVariable String formSlug,
            @PathVariable Integer questionId) {
        CommonResponse<QuestionResponse> response = questionService.getQuestionById(formSlug, questionId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<CommonResponse<QuestionResponse>> updateQuestion(
            @PathVariable String formSlug,
            @PathVariable Integer questionId,
            @Valid @RequestBody QuestionRequest request) {
        CommonResponse<QuestionResponse> response = questionService.updateQuestion(formSlug, questionId, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<CommonResponse<Void>> removeQuestion(
            @PathVariable String formSlug,
            @PathVariable Integer questionId) {
        questionService.removeQuestion(formSlug, questionId);
        return ResponseEntity.ok(new CommonResponse<>(200, "Remove question success", null));
    }
}
