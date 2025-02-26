package com.yourstech.springform.mapper;

import com.yourstech.springform.dto.response.UserResponse;
import com.yourstech.springform.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse registerMapper(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponse getUserResponse (User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
