package com.yourstech.springform.dto.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitDetailResponse {
    private String date;
    private UserResponse user;
    private Map<String, String> answers;
}
