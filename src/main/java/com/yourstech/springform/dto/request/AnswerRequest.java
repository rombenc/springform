package com.yourstech.springform.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {
    private Integer questionId;
    private String value;
}
