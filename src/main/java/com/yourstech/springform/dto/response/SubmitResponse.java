package com.yourstech.springform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitResponse {
    private Integer id;
    private Integer formId;
    private Integer userId;
    private List<AnswerResponse> answers;
    private String createdAt;


}
