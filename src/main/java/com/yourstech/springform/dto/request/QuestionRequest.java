package com.yourstech.springform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "The name field is required.")
    private String name;

    @NotBlank(message = "The choice type field is required.")
    private String choiceType;

    @NotNull(message = "The required field must be specified.")
    private Boolean isRequired;

    private List<String> choices;
}