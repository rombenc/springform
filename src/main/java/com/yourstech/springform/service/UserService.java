package com.yourstech.springform.service;

import com.yourstech.springform.dto.request.LoginRequest;
import com.yourstech.springform.dto.request.UserRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.LoginResponse;
import com.yourstech.springform.dto.response.UserResponse;
import com.yourstech.springform.model.User;
import jakarta.transaction.Transactional;

public interface UserService {
    @Transactional
    CommonResponse<Object> register(UserRequest request);

    @Transactional
    CommonResponse<Object> verifyAccount(String token);

    @Transactional
    CommonResponse<LoginResponse> login(LoginRequest request);

    @Transactional
    CommonResponse<Object> logout(String token);

    CommonResponse<UserResponse> findById(Integer id);

    User getLoginUser();
}
