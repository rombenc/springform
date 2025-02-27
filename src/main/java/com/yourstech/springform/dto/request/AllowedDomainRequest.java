package com.yourstech.springform.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllowedDomainRequest {
    private List<String> domain; // HARUS List<String>, bukan String
}
