package com.yourstech.springform.service;

import com.yourstech.springform.dto.request.FormRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.FormDetailResponse;
import com.yourstech.springform.dto.response.FormResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FormService {

    CommonResponse<FormResponse> createForm(FormRequest request);

    CommonResponse<List<FormResponse>> getAllForms();

    CommonResponse<FormDetailResponse> getFormBySlug(String slug);

    @Transactional
    CommonResponse<Void> deleteForm(String slug);
}