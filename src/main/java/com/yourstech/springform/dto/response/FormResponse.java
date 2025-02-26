package com.yourstech.springform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormResponse {
    private Integer id;
    private String slug;
    private String name;
    private String description;
    private Boolean limitOneResponse;
    private String allowedDomains;
}
