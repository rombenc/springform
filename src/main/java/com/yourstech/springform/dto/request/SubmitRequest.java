package com.yourstech.springform.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitRequest {
    private Integer userId;
    private List<AnswerRequest> answers;
}
