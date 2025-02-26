package com.yourstech.springform.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private Integer id;
    private String token;
}
