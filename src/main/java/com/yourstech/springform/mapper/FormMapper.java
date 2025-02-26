package com.yourstech.springform.mapper;

import com.yourstech.springform.dto.response.FormDetailResponse;
import com.yourstech.springform.dto.response.FormResponse;
import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.model.Form;
import com.yourstech.springform.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormMapper {

    public FormResponse form (Form form) {
        return FormResponse.builder()
                .id(form.getId())
                .slug(form.getSlug())
                .name(form.getName())
                .description(form.getDescription())
                .allowedDomains(String.valueOf(form.getAllowedDomains()))
                .limitOneResponse(form.getLimitOneResponse())
                .build();
    }

    public FormDetailResponse toFormDetailResponse(Form form) {
        if (form == null) {
            return null;
        }

        return FormDetailResponse.builder()
                .id(form.getId())
                .name(form.getName())
                .slug(form.getSlug())
                .description(form.getDescription())
                .limitOneResponse(form.getLimitOneResponse())
                .allowedDomains(String.valueOf(form.getAllowedDomains()))
                .questions(toQuestionResponseList(form.getQuestions()))
                .build();
    }

    private static List<QuestionResponse> toQuestionResponseList(List<Question> questions) {
        if (questions == null) {
            return List.of();
        }

        return questions.stream()
                .map(FormMapper::toQuestionResponse)
                .collect(Collectors.toList());
    }

    private static QuestionResponse toQuestionResponse(Question question) {
        if (question == null) {
            return null;
        }

        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .choiceType(String.valueOf(question.getChoiceType()))
                .choices(question.getChoices())
                .isRequired(question.getIsRequired())
                .build();
    }
}
