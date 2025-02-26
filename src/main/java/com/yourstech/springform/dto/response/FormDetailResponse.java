package com.yourstech.springform.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FormDetailResponse {
    private Integer id;
    private String name;
    private String slug;
    private String description;
    private Boolean limitOneResponse;
    private String allowedDomains;
    private List<QuestionResponse> questions;
}