package com.yourstech.springform.service.impl;

import com.yourstech.springform.dto.request.QuestionRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.mapper.QuestionMapper;
import com.yourstech.springform.model.Form;
import com.yourstech.springform.model.Question;
import com.yourstech.springform.model.enums.ChoiceType;
import com.yourstech.springform.repository.FormRepository;
import com.yourstech.springform.repository.QuestionRepository;
import com.yourstech.springform.service.QuestionService;
import com.yourstech.springform.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final QuestionMapper mapper;

    @Transactional
    @Override
    public CommonResponse<QuestionResponse> createQuestion(String formSlug, QuestionRequest request) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        Question question = Question.builder()
                .name(request.getName())
                .choiceType(ChoiceType.valueOf(request.getChoiceType()))
                .isRequired(request.getIsRequired())
                .choices(request.getChoices())
                .form(form)
                .build();
        questionRepository.save(question);
        return CommonResponse.<QuestionResponse>builder()
                .status(201)
                .message("Add question success")
                .data(mapper.general(question))
                .build();
    }

    @Override
    public CommonResponse<List<QuestionResponse>> getAllQuestions(String formSlug) {
        List<Question> questions = questionRepository.findByFormSlug(formSlug);
        List<QuestionResponse> questionResponses = mapper.listOfResponses(questions);

        return CommonResponse.<List<QuestionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all questions success")
                .data(questionResponses)
                .build();
    }

    @Override
    public CommonResponse<QuestionResponse> getQuestionById(String formSlug, Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return CommonResponse.<QuestionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get question success")
                .data(mapper.general(question))
                .build();
    }

    @Transactional
    @Override
    public CommonResponse<QuestionResponse> updateQuestion(String formSlug, Integer questionId, QuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        question.setName(request.getName());
        question.setChoiceType(ChoiceType.valueOf(request.getChoiceType()));
        question.setIsRequired(request.getIsRequired());
        question.setChoices(request.getChoices());
        questionRepository.save(question);
        return CommonResponse.<QuestionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Update question success")
                .data(mapper.general(question))
                .build();
    }

    @Override
    public void removeQuestion(String formSlug, Integer questionId) {
        Form form = formRepository.findBySlug(formSlug).orElseThrow(() -> new RuntimeException("Form not found"));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if (!question.getForm().getId().equals(form.getId())) {
            throw new RuntimeException("Forbidden access");
        }
        questionRepository.delete(question);
    }
}