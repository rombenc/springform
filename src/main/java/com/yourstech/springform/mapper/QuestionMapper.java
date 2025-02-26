package com.yourstech.springform.mapper;

import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionResponse general(Question question){
        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .choiceType(question.getChoiceType().name())
                .choices(question.getChoices())
                .isRequired(question.getIsRequired())
                .formId(question.getId())
                .build();
    }

    public List<QuestionResponse> listOfResponses(List<Question> questions) {
        return questions.stream()
                .map(this::general)
                .collect(Collectors.toList());
    }

}
