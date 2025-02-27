package com.yourstech.springform.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FormRequest {

    private String slug;
    private String name;
    private String description;
    private Boolean limitOneResponse;

}
