package com.yourstech.springform.mapper;

import com.yourstech.springform.dto.response.SubmitResponse;
import com.yourstech.springform.dto.response.AnswerResponse;
import com.yourstech.springform.model.Submit;
import com.yourstech.springform.model.Answer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubmitMapper {
    public static SubmitResponse toResponse(Submit submit, List<Answer> answers) {
        return SubmitResponse.builder()
                .id(submit.getId())
                .formId(submit.getForm().getId()) // Pastikan Form ID sesuai
                .userId(submit.getUser().getId()) // Ambil dari user_id di Submit
                .answers(answers.stream()
                        .map(answer -> new AnswerResponse(
                                answer.getQuestion().getId(), // Pastikan ambil dari question_id
                                answer.getValue()
                        ))
                        .collect(Collectors.toList()))
                .build();
    }
}