package com.yourstech.springform.dto.response;

import com.yourstech.springform.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponse {
    private Integer id;
    private String value;

}

